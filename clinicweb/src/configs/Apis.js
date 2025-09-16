import axios from "axios";
import cookie from 'react-cookies'

const BASE_URL = "http://localhost:8080/SpringClinicWeb/api";

export const endpoints = {
    "services": "/services",

    //specialize
    "specializes": "/specializes",

    //doctor
    "doctors": "/doctors",
    "addDoctorProfile": (userId) => `/doctors/${userId}/add-profile`,
    "choiceSpecialize": (userId) => `/doctors/${userId}/specializes`,

    //user
    "users": "/users",
    "login": "/login",
    "profile": "/secure/profile",

    //appointment
    "available-slots": "/available-slots",
    "appointments": "/appointments",
};

export const authApis = () => {
    return axios.create({
        baseURL: BASE_URL,
        headers: {
            'Authorization': `Bearer ${cookie.load('token')}`
        }
    })
}

export default axios.create({
    baseURL: BASE_URL
})