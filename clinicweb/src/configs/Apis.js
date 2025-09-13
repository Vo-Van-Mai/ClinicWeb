import axios from "axios";

const BASE_URL = "http://localhost:8080/SpringClinicWeb/api";

export const endpoints = {
    "services": "/services",

    //specialize
    "specializes": "/specializes",

    //doctor
    "doctors": "/doctors",
};

export default axios.create({
    baseURL: BASE_URL
})