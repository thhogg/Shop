/********************
 * Biến toàn cục
 *******************/

// productColors được khai báo trong JSP như:
// <script>let productColors = JSON.parse('${fn:escapeXml(productColorsJson)}');</script>
//
// Nên **không** khai báo lại hay gán giá trị mới ở đây!

var subCategoriesMap = {};
let colorsList = [];
let sizesList = [];

/********************
 * Load dữ liệu từ API (Subcategories, Colors, Sizes)
 *******************/

function loadSubcategoriesData() {
    return fetch('/Shop/api/loadSubCategories.do')
        .then(res => {
            if (!res.ok) throw new Error('Failed to load subcategories');
            return res.json();
        })
        .then(data => {
            subCategoriesMap = data;
            console.log('Subcategories loaded:', subCategoriesMap);
            updateSubcategories();
        });
}

function loadColorsData() {
    return fetch('/Shop/api/loadColors.do')
        .then(res => {
            if (!res.ok) throw new Error('Failed to load colors');
            return res.json();
        })
        .then(data => {
            colorsList = data;
            console.log('Colors loaded:', colorsList);
        });
}

function loadSizesData() {
    return fetch('/Shop/api/loadSizes.do')
        .then(res => {
            if (!res.ok) throw new Error('Failed to load sizes');
            return res.json();
        })
        .then(data => {
            sizesList = data;
            console.log('Sizes loaded:', sizesList);
        });
}

/********************
 * Cập nhật danh sách Subcategory theo Category chọn
 *******************/

function updateSubcategories() {
    const categorySelect = document.getElementById('categorySelect');
    const subcategorySelect = document.getElementById('subcategorySelect');
    const selectedCategoryId = categorySelect.value;

    subcategorySelect.innerHTML = '';

    if (!subCategoriesMap || Object.keys(subCategoriesMap).length === 0) {
        console.warn('Subcategories data not loaded yet');
        return;
    }

    const subcategories = subCategoriesMap[selectedCategoryId];

    if (subcategories && subcategories.length > 0) {
        subcategories.forEach(sc => {
            const opt = document.createElement('option');
            opt.value = sc.categoryId;
            opt.textContent = sc.categoryName;
            subcategorySelect.appendChild(opt);
        });
    } else {
        const opt = document.createElement('option');
        opt.value = '';
        opt.textContent = '-- Không có --';
        subcategorySelect.appendChild(opt);
    }
}

/********************
 * Tạo option cho select Màu
 *******************/

function createColorOptions(selectedColorId) {
    return colorsList.map(c => {
        return `<option value="${c.id}" ${c.id === selectedColorId ? 'selected' : ''}>${c.name}</option>`;
    }).join('');
}

/********************
 * Tạo option cho select Size
 *******************/

function createSizeOptions(selectedSizeId) {
    return sizesList.map(s => {
        return `<option value="${s.id}" ${s.id === selectedSizeId ? 'selected' : ''}>${s.name}</option>`;
    }).join('');
}

/********************
 * Khởi tạo giao diện màu sắc từ productColors
 *******************/

function initColorsFromData() {
    const container = document.getElementById('colorsContainer');
    container.innerHTML = '';

    if (!productColors || productColors.length === 0) {
        container.innerHTML = '<p>Không có dữ liệu màu sắc để hiển thị.</p>';
        return;
    }

    productColors.forEach((colorData, cIdx) => {
        const colorBlock = document.createElement('div');
        colorBlock.className = 'color-block';
        colorBlock.dataset.index = cIdx;

        colorBlock.innerHTML = `
            <h3 style="display:flex; justify-content:space-between; align-items:center;">
                Màu #${cIdx + 1}
                <button type="button" class="remove-btn" onclick="removeColorBlock(this, ${cIdx})">Xóa màu</button>
            </h3>

            <label>Tên màu:
                <select name="colors[${cIdx}][colorId]" required onchange="onColorChange(${cIdx}, this)">
                    ${createColorOptions(colorData.colorId)}
                </select>
            </label>

            <h4>Size & Số lượng</h4>
            <div class="sizesContainer" id="sizesContainer-${cIdx}"></div>
            <button type="button" onclick="addSize(${cIdx})">+ Thêm size</button>

            <h4>Ảnh sản phẩm</h4>
            <div class="imagesContainer" id="imagesContainer-${cIdx}"></div>
            <button type="button" onclick="addImage(${cIdx})">+ Thêm ảnh</button>

            <hr/>
        `;

        container.appendChild(colorBlock);

        // Khởi tạo size cho màu
        if (colorData.sizes && colorData.sizes.length > 0) {
            colorData.sizes.forEach(sizeData => {
                addSize(cIdx, sizeData.sizeId, sizeData.quantity);
            });
        }

        // Khởi tạo ảnh cho màu
        const imagesContainer = document.getElementById(`imagesContainer-${cIdx}`);
        if (colorData.imageUrls && colorData.imageUrls.length > 0) {
            colorData.imageUrls.forEach((img, iIdx) => {
                const imgDiv = document.createElement('div');
                imgDiv.className = 'image-item';
                imgDiv.style.marginBottom = '8px';
                imgDiv.style.display = 'flex';
                imgDiv.style.alignItems = 'center';
                imgDiv.style.gap = '8px';

                imgDiv.innerHTML = `
                    <span>${img.url}</span>
                    <label style="display:flex; align-items:center; gap:4px;">
                        <input type="checkbox" ${img.main === 1 ? 'checked' : ''} onclick="setMainImage(${cIdx}, ${iIdx}, this)" />
                        Ảnh chính
                    </label>
                    <img src="../${img.url}" alt="Preview" style="max-width:100px; max-height:100px; border:1px solid #ccc;" />
                    <button type="button" onclick="removeImage(${cIdx}, ${iIdx}, this)">Xóa</button>
                `;

                imagesContainer.appendChild(imgDiv);
            });
        }
    });
}

/********************
 * Thêm màu mới
 *******************/

function addColor() {
    const container = document.getElementById('colorsContainer');
    const newIndex = container.children.length;

    const defaultColorId = colorsList.length > 0 ? colorsList[0].colorId : null;

    productColors.push({
        colorId: defaultColorId,
        sizes: [],
        imageUrls: []
    });

    const colorBlock = document.createElement('div');
    colorBlock.className = 'color-block';
    colorBlock.dataset.index = newIndex;

    colorBlock.innerHTML = `
        <h3 style="display:flex; justify-content:space-between; align-items:center;">
            Màu #${newIndex + 1}
            <button type="button" class="remove-btn" onclick="removeColorBlock(this, ${newIndex})">Xóa màu</button>
        </h3>

        <label>Tên màu:
            <select name="colors[${newIndex}][colorId]" required onchange="onColorChange(${newIndex}, this)">
                ${createColorOptions(defaultColorId)}
            </select>
        </label>

        <h4>Size & Số lượng</h4>
        <div class="sizesContainer" id="sizesContainer-${newIndex}"></div>
        <button type="button" onclick="addSize(${newIndex})">+ Thêm size</button>

        <h4>Ảnh sản phẩm</h4>
        <div class="imagesContainer" id="imagesContainer-${newIndex}"></div>
        <button type="button" onclick="addImage(${newIndex})">+ Thêm ảnh</button>

        <hr/>
    `;

    container.appendChild(colorBlock);
}

/********************
 * Xóa block màu
 *******************/

function removeColorBlock(button, colorIndex) {
    const container = document.getElementById('colorsContainer');

    if (container.children.length <= 1) {
        alert('Phải có ít nhất một màu!');
        return;
    }

    // Xóa phần tử giao diện
    button.closest('.color-block').remove();

    // Xóa dữ liệu trong productColors
    productColors.splice(colorIndex, 1);

    // Cập nhật lại index các block màu còn lại
    updateColorBlocksIndex();
}

/********************
 * Cập nhật lại index các block màu và tên input form
 *******************/

function updateColorBlocksIndex() {
    const container = document.getElementById('colorsContainer');
    const blocks = container.querySelectorAll('.color-block');

    blocks.forEach((block, idx) => {
        block.dataset.index = idx;
        block.querySelector('h3').innerHTML = `
            Màu #${idx + 1} 
            <button type="button" class="remove-btn" onclick="removeColorBlock(this, ${idx})">Xóa màu</button>
        `;

        // Cập nhật tên select color
        const selectColor = block.querySelector('select[name^="colors"]');
        selectColor.name = `colors[${idx}][colorId]`;
        selectColor.setAttribute('onchange', `onColorChange(${idx}, this)`);

        // Cập nhật id container sizes và images
        const sizesContainer = block.querySelector('.sizesContainer');
        sizesContainer.id = `sizesContainer-${idx}`;
        const imagesContainer = block.querySelector('.imagesContainer');
        imagesContainer.id = `imagesContainer-${idx}`;

        // Cập nhật tên input size, quantity bên trong
        const sizeItems = sizesContainer.querySelectorAll('.size-item');
        sizeItems.forEach((sizeItem, sIdx) => {
            const selectSize = sizeItem.querySelector('select');
            const inputQty = sizeItem.querySelector('input[type="number"]');
            selectSize.name = `colors[${idx}][sizes][${sIdx}][sizeId]`;
            inputQty.name = `colors[${idx}][sizes][${sIdx}][quantity]`;
        });

        // Cập nhật onclick checkbox ảnh chính
        const imageItems = imagesContainer.querySelectorAll('.image-item');
        imageItems.forEach((imageItem, iIdx) => {
            const checkbox = imageItem.querySelector('input[type="checkbox"]');
            if (checkbox) {
                checkbox.setAttribute('onclick', `setMainImage(${idx}, ${iIdx}, this)`);
            }
        });
    });
}

/********************
 * Xử lý thay đổi màu sắc
 *******************/

function onColorChange(colorIndex, selectElem) {
    const newColorId = parseInt(selectElem.value);
    productColors[colorIndex].colorId = newColorId;
}

/********************
 * Thêm size mới
 *******************/

function addSize(colorIndex, sizeId = null, quantity = 0) {
    const sizesContainer = document.getElementById(`sizesContainer-${colorIndex}`);
    const sizeCount = sizesContainer.children.length;

    const div = document.createElement('div');
    div.className = 'size-item';
    div.style.marginBottom = '8px';

    div.innerHTML = `
        <select name="colors[${colorIndex}][sizes][${sizeCount}][sizeId]" required>
            ${createSizeOptions(sizeId)}
        </select>
        <input type="number" name="colors[${colorIndex}][sizes][${sizeCount}][quantity]" value="${quantity}" min="0" required style="width: 80px; margin-left: 10px;" />
        <button type="button" onclick="this.parentElement.remove(); updateProductColorsSizes(${colorIndex});">Xóa</button>
    `;

    sizesContainer.appendChild(div);

    // Gán sự kiện cập nhật dữ liệu
    const selectSize = div.querySelector('select');
    const inputQty = div.querySelector('input[type="number"]');
    selectSize.addEventListener('change', () => updateProductColorsSizes(colorIndex));
    inputQty.addEventListener('input', () => updateProductColorsSizes(colorIndex));

    updateProductColorsSizes(colorIndex);
}

/********************
 * Cập nhật dữ liệu size & quantity trong productColors
 *******************/

function updateProductColorsSizes(colorIndex) {
    const sizesContainer = document.getElementById(`sizesContainer-${colorIndex}`);
    const sizeItems = sizesContainer.querySelectorAll('.size-item');

    productColors[colorIndex].sizes = [];

    sizeItems.forEach(item => {
        const sizeId = parseInt(item.querySelector('select').value);
        const quantity = parseInt(item.querySelector('input[type="number"]').value) || 0;
        productColors[colorIndex].sizes.push({ sizeId, quantity });
    });
}

/********************
 * Thêm ảnh mới (upload)
 *******************/

function addImage(colorIndex) {
    const imagesContainer = document.getElementById(`imagesContainer-${colorIndex}`);
    const imageCount = imagesContainer.children.length;

    const div = document.createElement('div');
    div.className = 'image-item';
    div.style.marginBottom = '8px';
    div.style.display = 'flex';
    div.style.alignItems = 'center';
    div.style.gap = '8px';

    div.innerHTML = `
        <input type="file" name="productImageFile_${colorIndex}_${imageCount}" accept="image/*" required />
        <label style="display:flex; align-items:center; gap:4px;">
            <input type="checkbox" name="mainImage_${colorIndex}" onclick="setMainImage(${colorIndex}, ${imageCount}, this)" />
            Ảnh chính
        </label>
        <button type="button" onclick="this.parentElement.remove()">Xóa</button>
    `;

    imagesContainer.appendChild(div);
}

/********************
 * Xóa ảnh (hiện tại chỉ xóa giao diện, dữ liệu sẽ được cập nhật khi submit)
 *******************/

function removeImage(colorIndex, imageIndex, buttonElem) {
    if (productColors[colorIndex] && productColors[colorIndex].imageUrls) {
        productColors[colorIndex].imageUrls.splice(imageIndex, 1);
    }
    buttonElem.parentElement.remove();
}

/********************
 * Đánh dấu ảnh chính cho từng màu
 *******************/

function setMainImage(colorIndex, imageIndex, checkboxElem) {
    const imagesContainer = document.getElementById(`imagesContainer-${colorIndex}`);
    const checkboxes = imagesContainer.querySelectorAll('input[type="checkbox"]');
    checkboxes.forEach(cb => {
        if (cb !== checkboxElem) cb.checked = false;
    });

    if (productColors[colorIndex] && productColors[colorIndex].imageUrls) {
        productColors[colorIndex].imageUrls.forEach((img, idx) => {
            img.main = (idx === imageIndex && checkboxElem.checked) ? 1 : 0;
        });
    }
}

/********************
 * Submit form, chèn input ẩn JSON productColors
 *******************/

function handleAjaxSubmission() {
    const form = document.getElementById('productForm');

    // Cập nhật size dữ liệu trước khi submit
    productColors.forEach((_, idx) => updateProductColorsSizes(idx));

    // Tạo hoặc lấy input ẩn chứa JSON
    let jsonInput = form.querySelector('input[name="productColorsJson"]');
    if (!jsonInput) {
        jsonInput = document.createElement('input');
        jsonInput.type = 'hidden';
        jsonInput.name = 'productColorsJson';
        form.appendChild(jsonInput);
    }
    jsonInput.value = JSON.stringify(productColors);

    form.submit();
}

/********************
 * Khởi tạo khi load trang
 *******************/

window.addEventListener('load', () => {
    Promise.all([
        loadSubcategoriesData(),
        loadColorsData(),
        loadSizesData()
    ]).then(() => {
        initColorsFromData();
        updateSubcategories();
    }).catch(err => {
        console.error('Error loading initial data:', err);
    });
});