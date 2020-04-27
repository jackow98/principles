const axios = require('axios').default;

//Connection to API for retrieving educational data
export default axios.create({

    // For production
    // baseURL: "https://jackow98.site/"

    // For development
    baseURL: "http://localhost:8080/Principles/"
})