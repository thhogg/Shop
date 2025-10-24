/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function submitForm() {
    document.getElementById('priceForm').submit();
}


function submitProductForm() {
    document.getElementById('product__colorSizeForm').submit();
}


function enableImageClickRedirect() {
  document.querySelectorAll('.product__item').forEach(item => {
    const pic = item.querySelector('.product__item__pic');
    const link = item.querySelector('.product__item__text h6 a');

    if (pic && link) {
      pic.style.cursor = 'pointer'; // đổi con trỏ chuột thành pointer
      pic.addEventListener('click', (event) => {
        // Nếu click vào <li> hoặc <a> trong .product__hover, không chuyển hướng
        // Kiểm tra xem phần tử được click có nằm trong .product__hover không
        if (event.target.closest('.product__hover li a')) {
          // Bỏ qua sự kiện click chuyển hướng
          return;
        }
        window.location.href = link.href;
      });
    }
  });
}

// Gọi hàm sau khi DOM đã tải xong
document.addEventListener('DOMContentLoaded', enableImageClickRedirect);


