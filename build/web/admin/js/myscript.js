
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
            <select name="colors[${colorIndex}][colorId]" required>
                ${createColorOptions()}
            </select>
            <span id="preview-${colorIndex}" class="color-preview"></span>
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
                required>
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

let imageCounter = 0;

function addImage(colorIndex) {
    imageCounter++;
    const container = document.getElementById(`imagesContainer-${colorIndex}`);
    const imageIndexInArray = productColors[colorIndex].imageUrls.length; // Dùng độ dài mảng image hiện tại

    const div = document.createElement("div");
    div.className = "image-item";
    
    div.innerHTML = `
        <input type="text" 
            name="colors[${colorIndex}][imageUrls][${imageIndexInArray}]" 
            placeholder="Nhập URL ảnh" required
        />
        <button type="button" onclick="removeImage(this, ${colorIndex})">Xóa</button>
    `;

    container.appendChild(div);
    
    // Thêm một placeholder rỗng vào mảng JS
    productColors[colorIndex].imageUrls.push("");

    console.log(`Đã thêm input ảnh cho màu ${colorIndex} tại imageIndex: ${imageIndexInArray}`);
}


// Hàm hỗ trợ: Xóa Ảnh
function removeImage(buttonElement, colorIndex) {
    const imageItem = buttonElement.closest('.image-item');
    const imageContainer = imageItem.parentNode;
    const imageIndexInArray = Array.from(imageContainer.children).indexOf(imageItem);
    
    // 1. Xóa khỏi DOM
    imageItem.remove();
    
    // 2. Xóa khỏi mảng JS productColors
    productColors[colorIndex].imageUrls.splice(imageIndexInArray, 1);
    
    // 3. Tái lập chỉ mục ảnh trong DOM
    Array.from(imageContainer.children).forEach((item, newImageIndex) => {
        item.querySelectorAll('[name^="colors"]').forEach(element => {
            const oldName = element.getAttribute('name');
            // Thay thế chỉ mục ảnh cũ bằng chỉ mục ảnh mới trong tên: imageUrls[cũ] -> imageUrls[mới]
            const newName = oldName.replace(/imageUrls\[\d+\]/, `imageUrls[${newImageIndex}]`);
            element.setAttribute('name', newName);
            // Giữ nguyên giá trị (value) của input
        });
    });
    
    console.log(`Đã xóa ảnh tại index ${imageIndexInArray} của màu ${colorIndex}`);
}


/***************************
 * Hàm chuẩn bị dữ liệu trước khi Submit
 ***************************/
function prepareForm() {
       
    // 1. Chuyển mảng productColors thành chuỗi JSON
    const colorsJsonString = JSON.stringify(productColors);

    // 2. Gán chuỗi JSON vào trường ẩn
    document.getElementById("productColorsJson").value = colorsJsonString;

    // 3. Kiểm tra các trường khác của form (Tên, Giá, Category)
    // Đảm bảo bạn đã chọn Subcategory (Cần thêm trường name cho subcategorySelect)
    const subcategorySelect = document.getElementById("subcategorySelect");
    if (!subcategorySelect.value) {
        alert("Vui lòng chọn Subcategory trước khi lưu!");
        return false;
    }
      
    
    console.log("JSON submitted:", colorsJsonString);
    return true; // Cho phép form submit
}
