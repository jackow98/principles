// @ts-ignore
import loader from "./loader.css";
import React from "react";

export default function Loader(){
    return(
        <div style={loader} className="loader ease-linear rounded-full border-8 border-t-8 border-gray-200 h-16 w-16"/>
    )
}