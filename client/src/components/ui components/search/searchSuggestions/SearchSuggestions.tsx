import React from 'react';

export default function SearchSuggestions() {

    return (
        <div className="h-64">
            <div className="block border-2 rounded-lg overflow-y-scroll max-h-full z-10 bg-white">
                {
                    [0, 1, 2, 3, 4, 5, 6, 7, 8, 9].map(() => {
                        return <div
                            className="block w-full hover:bg-gray-200 hover:text-pink-600 font-semibold px-4 py-4">Test</div>
                    })
                }
            </div>
        </div>

    )
}