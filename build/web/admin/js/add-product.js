
/********************
 * Load Data
 *******************/


/*
 * 
 * Load Subcategory
 */

var subCategoriesMap = {};

function loadSubcategoriesData() {
    // URL Servlet
    fetch('/Shop/api/loadSubCategories.do')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json(); // Phân tích cú pháp JSON
            })
            .then(data => {
                // Lưu dữ liệu vào biến toàn cục đã khai báo
                subCategoriesMap = data;
                console.log("Subcategories data loaded successfully:", subCategoriesMap);
                // Khởi tạo lần đầu tiên sau khi có dữ liệu
                updateSubcategories();
            })
            .catch(error => {
                console.error("Error fetching subcategories data:", error);
                // Xử lý lỗi (ví dụ: hiển thị thông báo)
            });
}

function updateSubcategories() {
    var categorySelect = document.getElementById("categorySelect");
    var subcategorySelect = document.getElementById("subcategorySelect");
    // categorySelect.value là chuỗi, subCategoriesMap có khóa là chuỗi sau khi parse
    var selectedCategoryId = categorySelect.value;

    // Xóa hết options cũ
    subcategorySelect.innerHTML = "";

    // ️ Kiểm tra xem subCategoriesMap đã được tải chưa
    if (Object.keys(subCategoriesMap).length === 0) {
        console.log("Subcategories Map is empty or not yet loaded.");
        return;
    }

    var subcategories = subCategoriesMap[selectedCategoryId];

    if (subcategories && subcategories.length > 0) {
        subcategories.forEach(function (sc) {
            var option = document.createElement("option");
            // Đảm bảo value và text được thiết lập đúng
            option.value = sc.categoryId;
            option.text = sc.categoryName;
            subcategorySelect.appendChild(option);
        });
    } else {
        var option = document.createElement("option");
        option.value = "";
        option.text = "-- Không có --";
        subcategorySelect.appendChild(option);
    }
}

// Expose function ra global để onchange gọi được
//window.updateSubcategories = updateSubcategories;

//  Khởi tạo: Gọi hàm tải dữ liệu khi load trang
window.addEventListener('load', loadSubcategoriesData);


/************************ 
 *      Load Colors
 ************************/

let colorsList = [];

function loadColorsData() {
    // URL Servlet
    fetch('/Shop/api/loadColors.do')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json(); // Phân tích cú pháp JSON
            })
            .then(data => {
                // Lưu dữ liệu vào biến toàn cục đã khai báo
                colorsList = data;
                console.log("Colors data loaded successfully:", colorsList);
                updateAllColorSelects();
            })
            .catch(error => {
                console.error("Error fetching colors data:", error);
                // Xử lý lỗi (ví dụ: hiển thị thông báo)
            });
}

window.addEventListener('load', loadColorsData);


/************************ 
 *      Load Sizes
 ************************/

let sizesList = [];

function loadSizesData() {
    // URL Servlet
    fetch('/Shop/api/loadSizes.do')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json(); // Phân tích cú pháp JSON
            })
            .then(data => {
                // Lưu dữ liệu vào biến toàn cục đã khai báo
                sizesList = data;
                console.log("Sizes data loaded successfully:", sizesList);
                updateAllSizeSelects();
            })
            .catch(error => {
                console.error("Error fetching colors data:", error);
                // Xử lý lỗi (ví dụ: hiển thị thông báo)
            });
}

window.addEventListener('load', loadSizesData);


/***************************
 * Hàm tiện ích để tạo Options
 ***************************/
function createColorOptions(selectedValue = null) {
    let options = '<option value="">-- Chọn màu --</option>';

    // THAY ĐỔI: Lặp qua mảng colorsList
    colorsList.forEach(color => {
        // Lấy dữ liệu từ đối tượng trong mảng
        const colorId = color.id;
        const colorName = color.name;
        const hexCode = color.hexCode; // Có thể dùng cho xem trước màu

        const selectedAttr = (parseInt(colorId) === selectedValue) ? 'selected' : '';

        // Thêm thuộc tính data-hex vào option để dễ dàng hiển thị màu
        options += `<option value="${colorId}" ${selectedAttr} data-hex="${hexCode}">${colorName} (ID: ${colorId})</option>`;
    });

    return options;
}

function updateAllColorSelects() {
    document.querySelectorAll('.color-block select[name*="colorId"]').forEach(select => {
        const selectedValue = select.value;
        select.innerHTML = createColorOptions(parseInt(selectedValue));
    });
}


/***************************
 * Thêm Màu (Add Color)
 ***************************/

let productColors = [];
// Loại bỏ colorCount. productColors.length sẽ thay thế vai trò của nó

function addColor() {
    // 1. Chuẩn bị dữ liệu và thêm vào mảng
    const newColor = {
        colorId: null,
        imageUrls: [],
        sizes: []
    };
    productColors.push(newColor);

    // Sử dụng độ dài mới của mảng làm index
    const colorIndex = productColors.length - 1;

    // 2. Tạo giao diện HTML
    const container = document.getElementById("colorsContainer");
    const div = document.createElement("div");

    div.className = "color-block";
    div.dataset.index = colorIndex;

    div.innerHTML = `
        <h3 style="display: flex; justify-content: space-between; align-items: center;">
            Màu #${colorIndex + 1}
            <button type="button" class="remove-btn" onclick="removeColorBlock(this, ${colorIndex})">Xóa màu</button>
        </h3>
        
        <label>Tên màu:
            <select name="colors[${colorIndex}][colorId]" required onchange="onColorChange(${colorIndex}, this)">
                ${createColorOptions()}
            </select>          
        </label>

        
        <h4>Size & Số lượng</h4>
        <div class="sizesContainer" id="sizesContainer-${colorIndex}"></div>
        <button type="button" onclick="addSize(${colorIndex})">+ Thêm size</button>

        <h4>Ảnh sản phẩm</h4>
        <div class="imagesContainer" id="imagesContainer-${colorIndex}"></div>
        <button type="button" onclick="addImage(${colorIndex})">+ Thêm ảnh</button>
        
        <hr/>
    `;
    container.appendChild(div);

    console.log(`Đã thêm màu mới tại index: ${colorIndex}`);
}

/***********************************************************
 * Kiểm tra trùng màu
 ***********************************************************/

function isDuplicateColor(colorId, currentIndex) {
    return productColors.some((color, index) => index !== currentIndex && color.colorId === colorId);
}

function onColorChange(colorIndex, selectElement) {
    const selectedColorId = parseInt(selectElement.value);
    if (isDuplicateColor(selectedColorId, colorIndex)) {
        alert("Màu này đã được chọn ở biến thể khác. Vui lòng chọn màu khác.");
        selectElement.value = ""; // Reset lại chọn
        productColors[colorIndex].colorId = null;
        return false;
    } else {
        productColors[colorIndex].colorId = selectedColorId;
        return true;
    }
}


/***********************************************************
 * Hàm hỗ trợ: Cập nhật lại index cho cả mảng và DOM sau khi xóa
 * *Giữ nguyên các giá trị đã chọn trong input/select*
 ***********************************************************/
function reIndexColors() {
    const colorBlocks = document.querySelectorAll('#colorsContainer .color-block');

    // Tái lập chỉ mục (Re-index)
    colorBlocks.forEach((block, newIndex) => {
        const oldIndex = parseInt(block.dataset.index);

        // 1. Cập nhật data-index
        block.dataset.index = newIndex;

        // 2. Cập nhật Tiêu đề và nút Xóa màu
        const heading = block.querySelector('h3');
        if (heading) {
            heading.innerHTML = `Màu #${newIndex + 1}
                <button type="button" class="remove-btn" onclick="removeColorBlock(this, ${newIndex})">Xóa màu</button>`;
        }

        // 3. Cập nhật tất cả thuộc tính 'name' và 'onclick'

        // Thay thế chỉ mục màu cũ bằng chỉ mục màu mới (name="colors[oldIndex][...]" -> name="colors[newIndex][...]")
        const oldIndexRegex = new RegExp(`colors\\[${oldIndex}\\]`, 'g');
        const newIndexString = `colors[${newIndex}]`;

        block.querySelectorAll('[name^="colors"]').forEach(element => {
            const oldName = element.getAttribute('name');
            const newName = oldName.replace(oldIndexRegex, newIndexString);
            element.setAttribute('name', newName);

            // Nếu là input/select, giá trị (value) vẫn được giữ nguyên.
        });

        // Thay thế chỉ mục màu cũ bằng chỉ mục màu mới trong hàm onclick: func(oldIndex, ...) -> func(newIndex, ...)
        block.querySelectorAll('[onclick]').forEach(element => {
            const oldOnclick = element.getAttribute('onclick');
            // Regex tìm kiếm số đầu tiên sau "(" (là colorIndex)
            const newOnclick = oldOnclick.replace(/\((\d+)/, `(${newIndex}`);
            element.setAttribute('onclick', newOnclick);
        });

        // 4. Cập nhật ID container và Preview Span
        block.querySelector('.sizesContainer').id = `sizesContainer-${newIndex}`;
        block.querySelector('.imagesContainer').id = `imagesContainer-${newIndex}`;
        block.querySelector('.color-preview').id = `preview-${newIndex}`;
    });
}

/***************************
 * Hàm Xóa Màu (Remove)
 ***************************/
function removeColorBlock(buttonElement, index) {
    if (confirm("Bạn có chắc chắn muốn xóa khối màu này không?")) {
        // 1. Xóa phần tử khỏi mảng JS productColors
        productColors.splice(index, 1);

        // 2. Xóa khối HTML khỏi DOM
        buttonElement.closest('.color-block').remove();

        // 3. Tái lập chỉ mục các khối còn lại
        reIndexColors();

        console.log(`Đã xóa màu tại index: ${index}. Mảng mới:`, productColors);
    }
}




/***************************
 * Hàm tiện ích để tạo Options cho Size
 * Giả định sizesList là Array: [{id: 1, name: 'S'}, {id: 2, name: 'M'}, ...]
 ***************************/
function createSizeOptions(selectedValue = null) {
    let options = '<option value="">-- Chọn Size --</option>';

    sizesList.forEach(size => {
        const sizeId = size.id;
        const sizeName = size.name;

        const selectedAttr = (parseInt(sizeId) === selectedValue) ? 'selected' : '';

        // Gán SizeID làm value và hiển thị Tên Size
        options += `<option value="${sizeId}" ${selectedAttr}>${sizeName} (ID: ${sizeId})</option>`;
    });

    return options;
}

// Hàm cập nhật dropdown sau khi sizesList được tải
function updateAllSizeSelects() {
    document.querySelectorAll('.size-item select[name*="sizeId"]').forEach(select => {
        const selectedValue = select.value;
        select.innerHTML = createSizeOptions(parseInt(selectedValue));
    });
}

/***************************
 * Kiểm tra trùng size 
 ***************************/


function isDuplicateSize(colorIndex, sizeId, currentSizeIndex) {
    return productColors[colorIndex].sizes.some((size, idx) => idx !== currentSizeIndex && size.sizeId === sizeId);
}

function onSizeChange(colorIndex, sizeIndex, selectElement) {
    const selectedSizeId = parseInt(selectElement.value);
    if (isDuplicateSize(colorIndex, selectedSizeId, sizeIndex)) {
        alert("Size này đã được chọn trong biến thể màu này. Vui lòng chọn size khác.");
        selectElement.value = ""; // Reset lại
        productColors[colorIndex].sizes[sizeIndex].sizeId = null;
        return false;
    } else {
        productColors[colorIndex].sizes[sizeIndex].sizeId = selectedSizeId;
        return true;
    }
}



/***************************
 * Hàm Thêm Size (Add Size) 
 ***************************/
let sizeCounter = 0; //  sizeCounter để form là colors[c][sizes][i]

function addSize(colorIndex) {
    sizeCounter++; // Dùng để đảm bảo tên form là duy nhất nếu cần
    const container = document.getElementById(`sizesContainer-${colorIndex}`);

    // Sử dụng độ dài hiện tại để tạo chỉ mục cho tên form
    const sizeIndexInArray = productColors[colorIndex].sizes.length;

    const div = document.createElement("div");
    div.className = "size-item";

    div.innerHTML = `
        <label>
            <span>Size:</span>
            <select 
                name="colors[${colorIndex}][sizes][${sizeIndexInArray}][sizeId]" 
                required onchange="onSizeChange(${colorIndex}, ${sizeIndexInArray}, this)">
                ${createSizeOptions()}
            </select>
        </label>
    
        <label>
            <span>Quantity:</span>
            <input type="number" 
                name="colors[${colorIndex}][sizes][${sizeIndexInArray}][quantity]" 
                placeholder="Số lượng" min="0" required
            />
        </label>
        <button type="button" onclick="removeSize(this, ${colorIndex})">Xóa</button>
    `;

    container.appendChild(div);

    // Khởi tạo đối tượng size mới trong mảng JS
    productColors[colorIndex].sizes.push({sizeId: null, quantity: null});

    console.log(`Đã thêm size mới cho màu ${colorIndex} tại sizeIndex: ${sizeIndexInArray}`);
}

// Hàm hỗ trợ: Xóa Size và tái lập chỉ mục cấp độ 2
function removeSize(buttonElement, colorIndex) {
    const sizeItem = buttonElement.closest('.size-item');
    const sizeContainer = sizeItem.parentNode;

    // Tìm index của size trong DOM để tương ứng với mảng JS
    const sizeIndexInArray = Array.from(sizeContainer.children).indexOf(sizeItem);

    // 1. Xóa khỏi DOM
    sizeItem.remove();

    // 2. Xóa khỏi mảng JS productColors
    productColors[colorIndex].sizes.splice(sizeIndexInArray, 1);

    // 3. Tái lập chỉ mục size trong DOM 
    Array.from(sizeContainer.children).forEach((item, newSizeIndex) => {
        item.querySelectorAll('[name^="colors"]').forEach(element => {
            const oldName = element.getAttribute('name');
            // Regex thay thế chỉ mục size: [sizes][cũ][...] -> [sizes][mới][...]
            const newName = oldName.replace(/sizes\[\d+\]/, `sizes[${newSizeIndex}]`);
            element.setAttribute('name', newName);
            // Giữ nguyên giá trị (value) của input/select
        });
    });

    console.log(`Đã xóa size tại index ${sizeIndexInArray} của màu ${colorIndex}`);
}


/***************************
 * 3. Hàm Thêm Ảnh (addImage)
 ***************************/

let fileIdCounter = 0; // ID duy nhất cho mỗi input file

function addImage(colorIndex) {
    fileIdCounter++;
    const container = document.getElementById(`imagesContainer-${colorIndex}`);
    const uniqueFileId = fileIdCounter;
    const previewId = `previewImage-${colorIndex}-${uniqueFileId}`;

    const div = document.createElement("div");
    div.className = "image-item";
    div.dataset.key = uniqueFileId;
    div.style.marginBottom = "8px";
    div.style.display = "flex";
    div.style.alignItems = "center";
    div.style.gap = "8px";

    div.innerHTML = `
        <input type="file" 
            name="productImageFile_${colorIndex}_${uniqueFileId}" 
            required
            data-color-index="${colorIndex}" 
            data-file-key="${uniqueFileId}"
            onchange="previewImage(event, '${previewId}')"
        />      
        <label style="display:flex; align-items:center; gap:4px;">
            <input type="checkbox" 
                   name="mainImage_${colorIndex}_${uniqueFileId}" 
                   onclick="setMainImage(${colorIndex}, ${uniqueFileId}, this)">
            Ảnh chính
        </label>

        <img id="${previewId}" 
             src="#" alt="Image Preview" 
             style="max-width: 100px; max-height: 100px; border: 1px solid #ccc; display: none;" />

        <button type="button" onclick="removeImage(this)">Xóa</button>
    `;

    container.appendChild(div);
}


/***************************
 * Hàm chỉ cho phép 1 ảnh chính mỗi màu
 ***************************/

function setMainImage(colorIndex, fileId, checkbox) {
    const container = document.getElementById(`imagesContainer-${colorIndex}`);

    // Bỏ chọn tất cả checkbox khác
    container.querySelectorAll('input[type="checkbox"]').forEach(cb => {
        if (cb !== checkbox)
            cb.checked = false;
    });

    // Cập nhật trong productColors (nếu đã có fileName)
    if (productColors[colorIndex]) {
        productColors[colorIndex].imageUrls.forEach(img => img.main = 0);

        const fileInput = container.querySelector(`input[data-file-key="${fileId}"]`);
        if (fileInput && fileInput.files.length > 0) {
            const fileName = fileInput.files[0].name;
            const imgObj = productColors[colorIndex].imageUrls.find(img => img.url === fileName);
            if (imgObj)
                imgObj.main = 1;
        }
    }
}



/***************************
 * Hàm xóa ảnh
 ***************************/

function removeImage(buttonElement) {
    const imageItem = buttonElement.closest('.image-item');
    imageItem.remove();

    console.log(`Đã xóa input file và preview.`);
}

/***************************
 * Hàm hiển thị Preview ảnh
 ***************************/
function previewImage(event, previewImgId) {
    const reader = new FileReader();
    const imagePreview = document.getElementById(previewImgId);

    // Xử lý khi file đã được đọc xong
    reader.onload = function () {
        if (reader.readyState === 2) {
            imagePreview.src = reader.result;
            imagePreview.style.display = 'block'; // Hiện ảnh lên
        }
    }

    // Đảm bảo có file được chọn
    if (event.target.files[0]) {
        reader.readAsDataURL(event.target.files[0]); // Bắt đầu đọc file
    } else {
        // Xóa preview nếu người dùng hủy chọn file
        imagePreview.src = '#';
        imagePreview.style.display = 'none';
    }
}


/***************************
 * Hàm thu thập tên file
 ***************************/
//
//function collectFilenamesToJson() {
//    // 1. Reset/chuẩn bị mảng imageUrls trong productColors
//    productColors.forEach(color => {
//        color.imageUrls = []; // Sẽ chứa tên file (string)
//    });
//
//    // 2. Thu thập TÊN FILE và cập nhật productColors
//    document.querySelectorAll('input[type="file"]').forEach(fileInput => {
//        const files = fileInput.files;
//
//        if (files.length > 0) {
//            const fileName = files[0].name;
//
//            // Lấy colorIndex từ DOM (vì index này được duy trì bằng reIndexColors)
//            const colorIndexElement = fileInput.closest('.color-block');
//            if (!colorIndexElement)
//                return;
//
//            const colorIndex = parseInt(colorIndexElement.dataset.index);
//
//            // Tìm và thêm tên file vào mảng productColors
//            if (productColors[colorIndex]) {
//                productColors[colorIndex].imageUrls.push(fileName);
//            }
//        }
//    });
//}



/***************************
 * Hàm kiểm tra tính hợp lệ (Validation)
 ***************************/
function validateForm() {
    const form = document.getElementById('productForm');

    // Mảng lưu trữ các thông báo lỗi cụ thể
    let errorMessages = [];
    let isValid = true;

    // 1. Kiểm tra các trường bắt buộc cơ bản (Tên, Giá, Mô tả)
    const requiredInputs = form.querySelectorAll('input[required], textarea[required], select[required]');

    requiredInputs.forEach(input => {
        // Chỉ kiểm tra các trường không nằm trong các khối màu dynamic
        if (!input.closest('.color-block')) {
            if (!input.value.trim()) {
                // Thêm thông báo và đánh dấu lỗi
                errorMessages.push(`Trường bắt buộc còn trống: ${input.name || input.id}`);
                isValid = false;
            }
            // Loại bỏ style (Không thao tác style ở đây)
        }
    });

    // 2. Kiểm tra Subcategory
    const subcategorySelect = form.elements.subcategorySelect;
    if (!subcategorySelect || !subcategorySelect.value) {
        errorMessages.push("Vui lòng chọn một Subcategory.");
        isValid = false;
    }


    // 3. Kiểm tra khối Màu (Color Blocks)
    const colorBlocks = document.querySelectorAll('.color-block');
    if (colorBlocks.length === 0) {
        errorMessages.push("Vui lòng thêm ít nhất một biến thể màu.");
        return false;
    }

    colorBlocks.forEach((block, colorIndex) => {
        let blockErrors = false;

        // Kiểm tra tất cả các trường required bên trong khối màu
        block.querySelectorAll('[required]').forEach(input => {
            if (!input.value.trim()) {
                errorMessages.push(`[Màu #${colorIndex + 1}] Trường "${input.name}" còn trống.`);
                blockErrors = true;
                isValid = false;
            }
        });

        // Kiểm tra xem đã thêm ít nhất 1 size và 1 ảnh chưa (Nếu bạn muốn yêu cầu)
        const sizesCount = block.querySelectorAll('.size-item').length;
        const imagesCount = block.querySelectorAll('.image-item').length;

        if (sizesCount === 0) {
            errorMessages.push(`[Màu #${colorIndex + 1}] Vui lòng thêm ít nhất một Size & Số lượng.`);
            blockErrors = true;
            isValid = false;
        }

        if (imagesCount === 0) {
            errorMessages.push(`[Màu #${colorIndex + 1}] Vui lòng thêm ít nhất một Ảnh sản phẩm.`);
            blockErrors = true;
            isValid = false;
        } else {
            // Kiểm tra có ảnh chính không
            const hasMainImage = block.querySelectorAll('.image-item input[type="checkbox"]:checked').length > 0;
            if (!hasMainImage) {
                errorMessages.push(`[Màu #${colorIndex + 1}] Vui lòng chọn một ảnh làm Ảnh chính.`);
                blockErrors = true;
                isValid = false;
            }
        }


    });

    // 4. Hiển thị thông báo tổng hợp
    if (!isValid) {
        // Ghi log chi tiết (hữu ích cho việc debug và style sau này)
        console.error("Lỗi Validation Chi tiết:", errorMessages);

        // Hiển thị thông báo lỗi đơn giản cho người dùng
        const alertMessage = "Vui lòng kiểm tra và điền đầy đủ các thông tin sau:\n\n" + errorMessages.join('\n');
        alert(alertMessage);
    }

    return isValid;
}

/**************************
 * 
 * @returns {undefined}
 */

function updateProductColorsFromForm() {
    productColors.forEach((color, colorIndex) => {
        // Cập nhật colorId
        const colorSelect = document.querySelector(`select[name="colors[${colorIndex}][colorId]"]`);
        color.colorId = colorSelect ? parseInt(colorSelect.value) || null : null;

        // Cập nhật sizes:
        const sizeItems = document.querySelectorAll(`#sizesContainer-${colorIndex} .size-item`);
        color.sizes = [];
        sizeItems.forEach((sizeItem, sizeIndex) => {
            const sizeSelect = sizeItem.querySelector(`select[name="colors[${colorIndex}][sizes][${sizeIndex}][sizeId]"]`);
            const qtyInput = sizeItem.querySelector(`input[name="colors[${colorIndex}][sizes][${sizeIndex}][quantity]"]`);
            const sizeId = sizeSelect ? parseInt(sizeSelect.value) || null : null;
            const quantity = qtyInput ? parseInt(qtyInput.value) || null : null;
            color.sizes.push({sizeId, quantity});
        });
    });
}


function collectFilenamesToJson() {
    // Reset danh sách ảnh trong mỗi màu
    productColors.forEach(color => {
        color.imageUrls = [];
    });

    // Duyệt tất cả input file
    document.querySelectorAll('input[type="file"]').forEach(fileInput => {
        const files = fileInput.files;

        if (files.length > 0) {
            const fileName = files[0].name;

            const colorIndexElement = fileInput.closest('.color-block');
            if (!colorIndexElement)
                return;

            const colorIndex = parseInt(colorIndexElement.dataset.index);
            const container = document.getElementById(`imagesContainer-${colorIndex}`);
            const checkbox = container.querySelector(
                    `input[name="mainImage_${colorIndex}_${fileInput.dataset.fileKey}"]`
                    );

            const isMain = checkbox && checkbox.checked ? 1 : 0;

            if (productColors[colorIndex]) {
                productColors[colorIndex].imageUrls.push({
                    url: fileName,
                    main: isMain
                });
            }
        }
    });

    console.log("Dữ liệu ảnh sau khi thu thập:", productColors);
}


/***************************
 * Hàm Xử lý Submission Form Bằng AJAX (CÓ VALIDATION)
 ***************************/
function handleAjaxSubmission() {
    if (!validateForm())
        return;

    // Cập nhật dữ liệu màu, size...
    updateProductColorsFromForm();

    // Thu thập tên file vào imageUrls
    collectFilenamesToJson();

    const colorsJsonString = JSON.stringify(productColors);
    const jsonBlob = new Blob([colorsJsonString], {type: 'application/json'});

    const form = document.getElementById('productForm');
    // Tạo FormData từ form gốc để lấy tất cả input, file...
    const formData = new FormData(form);

    // Ghi đè hoặc thêm field productColorsJson dưới dạng Blob
    formData.set('productColorsJson', jsonBlob, 'productColors.json');

    fetch('add', {
        method: 'POST',
        body: formData
    })
            .then(res => {
                if (!res.ok)
                    throw new Error(`Server Error: ${res.status}`);
                return res.text();
            })
            .then(result => {
                alert("Add Product Successfully");
                console.log(result);
                window.location.href = "/Shop/admin/products";
            })
            .catch(error => {
                console.error("Lỗi gửi form:", error);
                alert("Lỗi: Không thể thêm sản phẩm. Vui lòng kiểm tra console.");
            });
}