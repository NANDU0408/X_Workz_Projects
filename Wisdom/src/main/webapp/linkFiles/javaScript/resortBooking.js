let fieldChecks = {
   "name": false,
    "email": false,
    "resort": false,
    "roomType": false,
    "checkin": false,
    "checkout": false
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

function resortValidation() {
  let element = document.getElementById("resort");
  let error = document.getElementById("resortError");

  if (element.value.trim() !== "") {
    error.innerHTML = "";
    fieldChecks["resort"] = true;
  } else {
    error.innerHTML = "Please select the Resort";
    fieldChecks["resort"] = false;
  }
  validateAndEnableSubmit();
}

function roomTypeValidation() {
    let elements = document.getElementsByName("roomType");
    let error = document.getElementById("roomTypeError");
    let isSelected = false;

    for (let i = 0; i < elements.length; i++) {
        if (elements[i].checked) {
            isSelected = true;
            break;
        }
    }

    if (isSelected) {
        error.innerHTML = "";
        fieldChecks["roomType"] = true;
    } else {
        error.innerHTML = "Please select a room type.";
        fieldChecks["roomType"] = false;
    }
    validateAndEnableSubmit();
}

function checkinValidation() {
    let element = document.getElementById("checkin");
    let error = document.getElementById("checkinError");

    if (element.value.trim() !== "") {
        error.innerHTML = "";
        fieldChecks["checkin"] = true;
    } else {
        error.innerHTML = "Please select a check-in date.";
        fieldChecks["checkin"] = false;
    }
    validateAndEnableSubmit();
}

function checkoutValidation() {
    let element = document.getElementById("checkout");
    let error = document.getElementById("checkoutError");

    if (element.value.trim() !== "") {
        error.innerHTML = "";
        fieldChecks["checkout"] = true;
    } else {
        error.innerHTML = "Please select a check-out date.";
        fieldChecks["checkout"] = false;
    }
    validateAndEnableSubmit();
}

function refreshPage() {
        location.reload();
    }
