document.addEventListener("DOMContentLoaded", function() {
    fetchCountries();
    document.getElementById("country").addEventListener("change", fetchStates);
    document.getElementById("state").addEventListener("change", fetchCities);
});

let getFields = {
    "type": false,
    "area": false,
    "address": false,
    "country": false,
    "state": false,
    "city": false,
    "description": false
};

function validate() {
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

async function fetchCountries() {
    try {
        const response = await fetch("https://countriesnow.space/api/v0.1/countries");
        const data = await response.json();
        const countryDropdown = document.getElementById("country");
        data.data.forEach(country => {
            let option = document.createElement("option");
            option.value = country.country;
            option.text = country.country;
            countryDropdown.add(option);
        });
    } catch (error) {
        console.error("Error fetching countries:", error);
    }
}

async function fetchStates() {
    const country = document.getElementById("country").value;
    if (country === "0") {
        getFields["country"] = false;
        validate();
        return;
    }
    getFields["country"] = true;

    try {
        const response = await fetch("https://countriesnow.space/api/v0.1/countries/states", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ country: country })
        });
        const data = await response.json();
        const stateDropdown = document.getElementById("state");
        stateDropdown.innerHTML = '<option value="0">Choose...</option>';
        if (data.data.states && data.data.states.length > 0) {
            data.data.states.forEach(state => {
                let option = document.createElement("option");
                option.value = state.name;
                option.text = state.name;
                stateDropdown.add(option);
            });
        }
        validateCountry();
    } catch (error) {
        console.error("Error fetching states:", error);
    }
}

async function fetchCities() {
    const country = document.getElementById("country").value;
    const state = document.getElementById("state").value;
    if (state === "0") {
        getFields["state"] = false;
        validate();
        return;
    }
    getFields["state"] = true;

    try {
        const response = await fetch("https://countriesnow.space/api/v0.1/countries/state/cities", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ country: country, state: state })
        });
        const data = await response.json();
        const cityDropdown = document.getElementById("city");
        cityDropdown.innerHTML = '<option value="0">Choose...</option>';
        if (data.data && data.data.length > 0) {
            data.data.forEach(city => {
                let option = document.createElement("option");
                option.value = city;
                option.text = city;
                cityDropdown.add(option);
            });
        }
        validateState();
    } catch (error) {
        console.error("Error fetching cities:", error);
    }
}

function setGroup() {
    let obj = document.getElementById("complaintType");
    let error = document.getElementById("complaintTypeError");

    if (obj.value !== "0") {
        error.innerHTML = "";
        getFields["type"] = true;
    } else {
        error.innerHTML = "Please select Complaint Type";
        error.style.color = 'red';
        getFields["type"] = false;
    }
    validate();
}

function setArea() {
    let name = document.getElementById("area");
    let error = document.getElementById("areaError");

    if (name.value.trim() === "" || name.value.length < 3 || name.value.length > 300) {
        error.innerHTML = "Please enter area (3-300 characters)";
        error.style.color = 'red';
        getFields["area"] = false;
    } else {
        getFields["area"] = true;
        error.innerHTML = "";
    }
    validate();
}

function setAddress() {
    let name = document.getElementById("address");
    let error = document.getElementById("addressError");

    if (name.value.trim() === "" || name.value.length < 3 || name.value.length > 300) {
        error.innerHTML = "Please enter a valid Address (3-300 characters)";
        error.style.color = 'red';
        getFields["address"] = false;
    } else {
        getFields["address"] = true;
        error.innerHTML = "";
    }
    validate();
}

function updateDescriptionCount() {
    let description = document.getElementById("description");
    let count = 300 - description.value.length;
    document.getElementById("descriptionCount").innerText = count + " characters remaining";

    let error = document.getElementById("descriptionError");
    if (description.value.trim() === "" || description.value.length > 300) {
        error.innerHTML = "Please enter a valid Description (1-300 characters)";
        error.style.color = 'red';
        getFields["description"] = false;
    } else {
        getFields["description"] = true;
        error.innerHTML = "";
    }
    validate();
}

function validateCountry() {
    let country = document.getElementById("country").value;
    let error = document.getElementById("countryError");

    if (country === "0") {
        error.innerHTML = "Please select a country";
        error.style.color = 'red';
        getFields["country"] = false;
    } else {
        error.innerHTML = "";
        getFields["country"] = true;
    }
    validate();
}

function validateState() {
    let state = document.getElementById("state").value;
    let error = document.getElementById("stateError");

    if (state === "0") {
        error.innerHTML = "Please select a state";
        error.style.color = 'red';
        getFields["state"] = false;
    } else {
        error.innerHTML = "";
        getFields["state"] = true;
    }
    validate();
}

function validateCity() {
    let city = document.getElementById("city").value;
    let error = document.getElementById("cityError");

    if (city === "0") {
        error.innerHTML = "Please select a city";
        error.style.color = 'red';
        getFields["city"] = false;
    } else {
        error.innerHTML = "";
        getFields["city"] = true;
    }
    validate();
}

function validateDescription() {
    let description = document.getElementById("description");
    let error = document.getElementById("descriptionError");

    if (description.value.trim() === "" || description.value.length > 300) {
        error.innerHTML = "Please enter a valid Description (1-300 characters)";
        error.style.color = 'red';
        getFields["description"] = false;
    } else {
        getFields["description"] = true;
        error.innerHTML = "";
    }
    validate();
}


function refreshPage() {
    window.location.reload();
}