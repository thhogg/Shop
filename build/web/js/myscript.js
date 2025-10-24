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


function formatVNDPrices(selectors = ['.product__price', '.product__details__price'], locale = 'en-US', symbol = '₫') {
  selectors.forEach(selector => {
    document.querySelectorAll(selector).forEach(el => {
      if (selector === '.product__details__price') {
        // Xử lý text node chính (không lấy text trong span)
        let textNodes = Array.from(el.childNodes).filter(n => n.nodeType === Node.TEXT_NODE);
        textNodes.forEach(textNode => {
          const m = textNode.textContent.match(/-?\d+(?:[.,]\d+)?/);
          if (!m) return;

          let raw = m[0].replace(',', '.');
          let n = parseFloat(raw);
          if (Number.isNaN(n)) return;

          if (Math.abs(n - Math.round(n)) < 1e-9) {
            n = Math.round(n);
          }

          textNode.textContent = n.toLocaleString(locale) + symbol + ' ';
        });

        // Xử lý <span> con nếu có
        const span = el.querySelector('span');
        if (span) {
          const m = span.textContent.match(/-?\d+(?:[.,]\d+)?/);
          if (!m) return;

          let raw = m[0].replace(',', '.');
          let n = parseFloat(raw);
          if (Number.isNaN(n)) return;

          if (Math.abs(n - Math.round(n)) < 1e-9) {
            n = Math.round(n);
          }

          span.textContent = n.toLocaleString(locale) + symbol;
        }

      } else {
        // Xử lý các selector khác - ví dụ .product__price
        const m = el.textContent.match(/-?\d+(?:[.,]\d+)?/);
        if (!m) return;

        let raw = m[0].replace(',', '.');
        let n = parseFloat(raw);
        if (Number.isNaN(n)) return;

        if (Math.abs(n - Math.round(n)) < 1e-9) {
          n = Math.round(n);
        }

        el.textContent = n.toLocaleString(locale) + symbol;
      }
    });
  });
}

// tự động chạy khi DOM sẵn sàng
document.addEventListener('DOMContentLoaded', () => {
  formatVNDPrices();
});


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


