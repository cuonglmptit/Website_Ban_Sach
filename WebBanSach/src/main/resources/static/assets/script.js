var r = document.querySelector(":root");
function randomColor() {
    var randomColor1 = "#" + (Math.floor(Math.random() * 2 ** 24)).toString(16).padStart(6, "0");
    var randomColor2 = "#" + (Math.floor(Math.random() * 2 ** 24)).toString(16).padStart(6, "0");
    console.log("mau1:" + randomColor1);
    console.log("mau2:" + randomColor2);
    r.style.setProperty('--mau1', randomColor1);
    r.style.setProperty('--mau2', randomColor2);
}

function displayFile() {
    var input = document.getElementById("uploadFile");
    var fileName = input.files[0].name;
    var fileDisplay = document.getElementById("fileDisplay");
    fileDisplay.textContent = fileName;
    var file = input.files[0];
    var img = document.querySelector("#coverImg img");
    var coverUrl = document.getElementById("bookCoverUrlImgInput");
    // Kiểm tra xem người dùng đã chọn tệp hình ảnh hay chưa
    if (file) {
        var reader = new FileReader();

        // Đọc tệp hình ảnh và hiển thị nó trong thẻ img
        reader.onload = function (event) {
            var imageUrl = event.target.result;
            img.src = imageUrl;
            document.getElementById("fileDisplay").textContent = file.name;
            coverUrl.value = "notnull";
        }

        reader.readAsDataURL(file);
    }
    else {
        img.src = "";
        document.getElementById("fileDisplay").textContent = "Chưa chọn ảnh nào";
    }
}

function validateSignInForm() {
    var username = document.getElementById("username").value;
    if (username.includes(' ')) {
        alert("Tên tài khoản không được chứa khoảng trắng!");
        return false;
    }
}


function validateSignUpForm() {
    var username = document.getElementById("username").value;
    var name = document.getElementById("name").value;
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirm_password").value;
    var email = document.getElementById("email").value;
    // var phone = document.getElementById("phone").value;

    var errorMessage = "";

    if (username.includes(' ')) {
        errorMessage += "Tên tài khoản không được chứa khoảng trắng!\n";
    }

    if (!name) {
        errorMessage += "Vui lòng nhập tên!\n";
    }

    if (!username) {
        errorMessage += "Vui lòng nhập tên tài khoản!\n";
    }

    if (!password) {
        errorMessage += "Vui lòng nhập mật khẩu!\n";
    }

    if (password !== confirmPassword) {
        errorMessage += "Mật khẩu nhập lại không khớp!\n";
    }

    if (!email) {
        errorMessage += "Vui lòng nhập email!\n";
    }

    // if (!phone) {
    //     errorMessage += "Vui lòng nhập số điện thoại!\n";
    // } else {
    //     var phonePattern = /^\+?\d+$/; // Biểu thức chính quy để kiểm tra số điện thoại
    //     if (!phonePattern.test(phone)) {
    //         errorMessage += "Số điện thoại không hợp lệ!\n";
    //     }
    // }

    if (errorMessage !== "") {
        alert(errorMessage);
        return false;
    }
}

function validateBookForm() {
    var title = document.getElementById("titleInput").value;
    var author = document.getElementById("authorInput").value;
    var descr = document.getElementById("descrInput").value;
    var rlsdate = document.getElementById("rlsdateInput").value;
    var pages = document.getElementById("pagesInput").value;
    // var uploadFile = document.getElementById("uploadFile");
    var coverUrl = document.getElementById("bookCoverUrlImgInput").value;
//    console.log(coverUrl);
    var errorMessage = "";

    if (title === "") {
        errorMessage += "Vui lòng nhập tiêu đề.\n";
    }
    if (author === "") {
        errorMessage += "Vui lòng nhập tác giả.\n";
    }
    if (descr === "") {
        errorMessage += "Vui lòng nhập mô tả về sách.\n";
    }
    if (rlsdate === "") {
        errorMessage += "Vui lòng chọn ngày phát hành.\n";
    }
    if (pages === "") {
        errorMessage += "Vui lòng nhập số trang.\n";
    }
    if (coverUrl === "") {
        errorMessage += "Vui lòng chọn tệp tin ảnh.\n";
    }

    if (errorMessage !== "") {
        // Hiển thị thông báo lỗi nếu có trường bị thiếu
        alert(errorMessage);
        return false;
    }else{
		var successMessage = "";
		successMessage += "Xác nhận sách: ";
		successMessage += title;
		return confirm(successMessage);
	}
}

function toggleEdit() {
    var inputs = document.getElementsByTagName("input");
    var textareas = document.getElementsByTagName("textarea");
    var selects = document.getElementsByTagName("select");
    var editButton = document.querySelector(".footer button");

    if (editButton.innerText === "Edit") {
        editButton.innerText = "Save";
        editButton.classList.remove("btn-light");
        editButton.classList.add("btn-primary");

        for (var i = 0; i < inputs.length; i++) {
            inputs[i].removeAttribute("disabled");
        }

        for (var j = 0; j < textareas.length; j++) {
            textareas[j].removeAttribute("disabled");
        }

        for (var k = 0; k < selects.length; k++) {
            selects[k].removeAttribute("disabled");
        }
    }else{
		if(validateBookForm() === true) {
			document.getElementById("bookForm").submit();
		}
	}
}
