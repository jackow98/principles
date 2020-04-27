import React from "react";

export default function ErrorMessage({message}: { message: string }) {
    return (
        <div
            className="bg-bred-500 border-2 p-2 h-auto flex overflow-hidden w-auto mt-4 rounded-lg md:border-0 m-2 mr-2 bg-red-600 md:ml-0 md:mr-0 md:block">
            <div className="text-center p-2">
                <div className="text-2xl text-white">{message}</div>
            </div>
        </div>
    )
}

