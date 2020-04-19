import React from "react";
import {Principle} from "./Results";

export default function Result({principle}:{principle:Principle}) {
    return (
        <div className="border-2 mt-2 p-2 md:rounded-lg h-auto flex overflow-hidden w-full">
            <div className="text-center p-2 w-3/12 md:w-2/12">
                <div className="font-bold block text-4xl text-pink-600">{principle.id}</div>
                <div className="block">{principle.topic}</div>
            </div>
            <div className="pl-4 w-9/12 md:w-10/12">
                <div className="text-2xl md:text-3xl">{principle.text}</div>
            </div>
        </div>
    )
}

