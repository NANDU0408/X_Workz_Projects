let fieldChecks = {
  "name": false,
  "age": false,
  "gender": false,
  "maritalStatus": false,
  "religion": false,
  "job": false,
  "qualification": false,
  "lookingFor": false
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

    if (element.value.trim().length >= 5 && element.value.trim().length <= 20) {
        error.innerHTML = "";
        fieldChecks["name"] = true;
    } else {
        error.innerHTML = "Invalid name. Add characters between 5 and 20 characters long";
        fieldChecks["name"] = false;
    }
    validateAndEnableSubmit();
}

function ageValidation() {
    let element = document.getElementById("age");
    let error = document.getElementById("ageError");
    let age = parseInt(element.value.trim());

    if (!isNaN(age) && age >= 21 && age <= 40) {
        error.innerHTML = "";
        fieldChecks["age"] = true;
    } else {
        error.innerHTML = "Age must be between 21 and 40";
        fieldChecks["age"] = false;
    }
    validateAndEnableSubmit();
}

function genderValidation() {
    let radios = document.getElementsByName("gender");
    let error = document.getElementById("genderError");

    // Loop through each radio button to check if one is selected
    let isChecked = false;
    for (let i = 0; i < radios.length; i++) {
        if (radios[i].checked) {
            isChecked = true;
            break;
        }
    }

    if (isChecked) {
        error.innerHTML = "";
        fieldChecks["gender"] = true;
    } else {
        error.innerHTML = "Please select the gender.";
        fieldChecks["gender"] = false;
    }
    validateAndEnableSubmit();
}

function maritalStatusValidation() {
  let element = document.getElementById("maritalStatus");
  let error = document.getElementById("maritalStatusError");

  if (element.value.trim() !== "") {
    error.innerHTML = "";
    fieldChecks["maritalStatus"] = true;
  } else {
    error.innerHTML = "Please select the maritalStatus";
    fieldChecks["maritalStatus"] = false;
  }
  validateAndEnableSubmit();
}

function religionValidation() {
  let element = document.getElementById("religion");
  let error = document.getElementById("religionError");

  if (element.value.trim() !== "") {
    error.innerHTML = "";
    fieldChecks["religion"] = true;
  } else {
    error.innerHTML = "Please select the religion";
    fieldChecks["religion"] = false;
  }
  validateAndEnableSubmit();
}

function jobValidation() {
    let element = document.getElementById("job");
    let error = document.getElementById("jobError");

    if (element.value.trim().length >= 2 && element.value.trim().length <= 20) {
        error.innerHTML = "";
        fieldChecks["job"] = true;
    } else {
        error.innerHTML = "Invalid input. Add characters between 2 and 20 characters long";
        fieldChecks["job"] = false;
    }
    validateAndEnableSubmit();
}

function qualificationValidation() {
  let element = document.getElementById("qualification");
  let error = document.getElementById("qualificationError");

  if (element.value.trim() !== "") {
    error.innerHTML = "";
    fieldChecks["qualification"] = true;
  } else {
    error.innerHTML = "Please select the qualification";
    fieldChecks["qualification"] = false;
  }
  validateAndEnableSubmit();
}


function lookingForValidation() {
    let element = document.getElementById("lookingFor");
    let error = document.getElementById("lookingForError");

    if (element.value.trim().length >= 5 && element.value.trim().length <= 500) {
        error.innerHTML = "";
        fieldChecks["lookingFor"] = true;
    } else {
        error.innerHTML = "Invalid input. Add characters between 5 and 500 characters long";
        fieldChecks["lookingFor"] = false;
    }
    validateAndEnableSubmit();
}

function refreshPage() {
        location.reload();
    }
