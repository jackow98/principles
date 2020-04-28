import React from "react";
import {Principle} from "./Results";

export default function Result({principle}: { principle: Principle }) {
    return (
        <div
            className="border-2 p-2 h-auto flex overflow-hidden w-auto mt-4 rounded-lg md:border-0 m-2 mr-2 md:ml-0 md:mr-0 md:block">
            <div className="text-center p-2 w-auto md:text-left">
                <div className="font-bold block text-xl text-pink-600 md:text-1xl">{principle.topic}</div>
                <div className="block">{principle.id}</div>
            </div>
            <div className="pl-4 max-w-9/12 md:pl-2">
                <div className="text-1xl md:text-2xl">{principle.principleText}</div>
            </div>
        </div>
    )
}

