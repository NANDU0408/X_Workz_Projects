let fieldChecks = {
  "name": false,
  "email": false,
  "mobile": false,
  "comments": false
//  "accept": false
};

function validateAndEnableSubmit() {
  let flag = true;

  for (let [key, value] of Object.entries(fieldChecks)) {
    if (value === false) {
      flag = false;
      break;
    }
  }

  // Enable or disable the submit button based on the flag
  if (flag) {
    document.getElementById("submitButton").removeAttribute("disabled");
  } else {
    document.getElementById("submitButton").setAttribute("disabled", "true");
  }
}

function nameValidation() {
    let element = document.getElementById("name");
    let error = document.getElementById("nameError");
    let namePattern = /^[a-zA-Z\s-]{3,30}$/;

    if (element.value.trim().length >= 3 && element.value.trim().length <= 30 && namePattern.test(element.value.trim())) {
        error.innerHTML = "";
        fieldChecks["name"] = true;
    } else if (!namePattern.test(element.value.trim())) {
        error.innerHTML = "Invalid name. Only letters, spaces, and hyphens are allowed, with a length between 3 and 30 characters.";
        fieldChecks["name"] = false;
    } else {
        error.innerHTML = "Invalid name. Ensure it is between 3 and 30 characters long.";
        fieldChecks["name"] = false;
    }
    validateAndEnableSubmit();
}

function emailValidation() {
    let element = document.getElementById("email");
    let error = document.getElementById("emailError");
    let emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;

    if (element.value.trim().length >= 3 && element.value.trim().length <= 30 && emailPattern.test(element.value.trim())) {
        error.innerHTML = "";
        fieldChecks["email"] = true;
    } else if(!emailPattern.test(element)){
                document.getElementById("emailError").innerText = "Invalid email format.";
                isValid = false;
    }else {
        error.innerHTML = "Invalid email-id. Ensure it is between 3 and 30 characters long and in a valid format.";
        fieldChecks["email"] = false;
    }
    validateAndEnableSubmit();
}


//let email = document.getElementById("email").value;
//        let emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
//        if (email === "") {
//            document.getElementById("emailError").innerText = "Email is required.";
//            isValid = false;
//        } else if (!emailPattern.test(email)) {
//            document.getElementById("emailError").innerText = "Invalid email format.";
//            isValid = false;
//        } else {
//            document.getElementById("emailError").innerText"";
//}

function mobileValidation() {
    let element = document.getElementById("mobile");
    let error = document.getElementById("mobileError");
    let mobile = element.value.trim();

    let mobilePattern = /^\d{10,12}$/;

    if (mobilePattern.test(mobile)) {
        error.innerHTML = "";
        fieldChecks["mobile"] = true;
    } else {
        error.innerHTML = "Mobile number must be between 10 and 12 digits";
        fieldChecks["mobile"] = false;
    }
    validateAndEnableSubmit();
}

function commentsValidation() {
    let element = document.getElementById("comments");
    let error = document.getElementById("commentsError");

    if (element.value.trim().length >= 30 && element.value.trim().length <= 300) {
        error.innerHTML = "";
        fieldChecks["comments"] = true;
    } else {
        error.innerHTML = "Invalid. Add characters between 30 and 300 characters long";
        fieldChecks["comments"] = false;
    }
    validateAndEnableSubmit();
}

function refreshPage() {
        location.reload();
    }
