let fieldChecks = {
  "firstName": false,
  "lastName": false,
  "emailAddress": false,
  "mobileNumber": false,
  "agreeTerms": false
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
  let firstNameElement = document.getElementById("firstName");
  let lastNameElement = document.getElementById("lastName");
  let firstNameError = document.getElementById("firstNameError");
  let lastNameError = document.getElementById("lastNameError");

  if (firstNameElement.value.trim().length >= 3 && firstNameElement.value.trim().length <= 20) {
    firstNameError.innerHTML = "";
    fieldChecks["firstName"] = true;
  } else {
    firstNameError.innerHTML = "First name must be between 3 and 20 characters long.";
    fieldChecks["firstName"] = false;
  }

  if (lastNameElement.value.trim().length >= 3 && lastNameElement.value.trim().length <= 20) {
    lastNameError.innerHTML = "";
    fieldChecks["lastName"] = true;
  } else {
    lastNameError.innerHTML = "Last name must be between 3 and 20 characters long.";
    fieldChecks["lastName"] = false;
  }

  validateAndEnableSubmit();
}

function emailAddressValidation() {
  let element = document.getElementById("emailAddress");
  let error = document.getElementById("emailAddressError");
  let emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;

  if (emailPattern.test(element.value.trim())) {
    error.innerHTML = "";
    fieldChecks["emailAddress"] = true;
  } else {
    error.innerHTML = "Invalid email address.";
    fieldChecks["emailAddress"] = false;
  }
  validateAndEnableSubmit();
}

function phoneValidation() {
  let element = document.getElementById("mobileNumber");
  let error = document.getElementById("mobileNumberError");
  let phonePattern = /^[0-9]{10}$/;

  if (phonePattern.test(element.value.trim())) {
    error.innerHTML = "";
    fieldChecks["mobileNumber"] = true;
  } else {
    error.innerHTML = "Mobile number must be 10 digits.";
    fieldChecks["mobileNumber"] = false;
  }
  validateAndEnableSubmit();
}

function termsValidation() {
  let element = document.getElementById("agreeTerms");
  let error = document.getElementById("termsError");

  if (element.checked) {
    error.innerHTML = "";
    fieldChecks["agreeTerms"] = true;
  } else {
    error.innerHTML = "You must agree to the terms and conditions.";
    fieldChecks["agreeTerms"] = false;
  }
  validateAndEnableSubmit();
}

function refreshPage() {
  window.location.reload();
}