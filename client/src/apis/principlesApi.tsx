const axios = require('axios').default;

//Connection to API for retrieving educational data
export default axios.create({

    // For production
    baseURL: "https://principles.jackow98.site/Principles/"

    // For development
    // baseURL: "http://localhost:8080/Principles/"
})