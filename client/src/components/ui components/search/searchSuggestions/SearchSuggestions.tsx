import React from 'react';

export default function SearchSuggestions({searchSuggestionsRef, updateSearchTerm}: any) {

    return (
        <div className="container bg-white w-auto h-auto mx-auto md:pt-2" ref={searchSuggestionsRef}>
            <div className="h-64  w-auto overflow-auto shadow-xl md:rounded-lg border-t-0 md:border-2">
                {
                    ["Education", "Technology", "Life", "Self"].map((value) => {
                        return <div
                            key={value}
                            className="cursor-pointer w-full hover:bg-gray-200 hover:text-pink-600 font-semibold px-4 py-4"
                            onClick={() => updateSearchTerm(value)}
                        >{value}
                        </div>
                    })
                }
            </div>
        </div>
    )

}