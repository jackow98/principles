import React from 'react';
import Loader from "../../loader/Loader";
import search from "../../../../assets/icons/search.svg";

interface SearchBarProps {
    loading: boolean;
    onSearch: () => void;
    updateSearchTerm: (value: string) => void;
    placeholder: string,
    focus: boolean,
    searchInputRef: any,
    setFocus: any
}

export default function SearchBar({loading, onSearch, updateSearchTerm, placeholder, searchInputRef, setFocus}: SearchBarProps) {

    return (
        <div className="container h-24 mx-auto md:mt-24">
            <div className="flex relative h-full mb-4 bg-gray-200 md:rounded-lg">
                <div className="w-2/12 md:w-1/12">
                    {loading ?
                        <div className="h-full w-full pt-8 pb-8 pl-2 pr-2 md:p-5"><Loader/></div> :
                        <img className="h-full w-full p-2 md:p-5" src={search} alt={""}/>
                    }
                </div>
                <input
                    ref={searchInputRef}
                    className="outline-none border-2 border-gray-200 text-xl pl-8 bg-gray-200 h-full w-full focus:bg-white focus:border-pink-600 md:rounded-lg"
                    type="text"
                    placeholder={placeholder}
                    onKeyDown={({key}) => {
                        if (key === 'Enter') {
                            onSearch();
                            setFocus(false);
                        }
                        if (key === 'ESC') {
                            setFocus(false);
                        }
                    }}
                    onFocus={() => setFocus(true)}
                    onChange={(e) => updateSearchTerm(e.target.value)}
                />
            </div>
        </div>
    )
}