// Import our hooks
import React, {useEffect, useRef} from 'react';

import Loader from "./Loader";
import search from "../assets/icons/search.svg";

interface SearchBarProps {
    loading: boolean;
    onSearch: () => void;
    placeholder: string
}


export default function SearchBar({loading, onSearch, placeholder}: SearchBarProps) {

    const searchInputRef = useRef<HTMLDivElement>(null);
    useEffect(() => {
        searchInputRef.current.focus();
    });

    return (
        <div className="container md:mt-24 w-auto h-24 mx-auto">
            <div className="flex relative h-full mb-4 md:rounded-lg">
                <div className="p-5 w-3/12 md:w-1/12">
                    {loading ?
                        <div className="h-auto w-full"><Loader/></div> :
                        <img className="h-full w-full" src={search} alt={""}/>
                    }
                </div>
                <input
                    ref={searchInputRef}
                    className="rounded-lg text-xl pl-8 bg-gray-200 h-full w-11/12 focus:bg-white"
                    type="text"
                    placeholder={placeholder}
                    onKeyDown={({key}) => {if (key === 'Enter') onSearch()}}
                />
            </div>
        </div>
    )
}