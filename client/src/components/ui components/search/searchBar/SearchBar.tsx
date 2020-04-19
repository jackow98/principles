import React, {useEffect, useRef, useState} from 'react';
import Loader from "../../loader/Loader";
import search from "../../../../assets/icons/search.svg";
import SearchSuggestions from "../searchSuggestions/SearchSuggestions";

interface SearchBarProps {
    loading: boolean;
    onSearch: () => void;
    placeholder: string
}

export default function SearchBar({loading, onSearch, placeholder}: SearchBarProps) {

    const[focus, setFocus] = useState(false)
    const searchInputRef = useRef<HTMLInputElement>(null);

    useEffect(() => {
        if (searchInputRef.current){
            searchInputRef.current.focus();
            setFocus(true)
        }
    },[]);

    return (
        <div className="container md:mt-24 w-auto h-24 mx-auto">
            <div className="flex relative h-full mb-4 bg-gray-200 md:rounded-lg">
                <div className="p-5 w-3/12 md:w-1/12">
                    {loading ?
                        <div className="h-auto w-full"><Loader/></div> :
                        <img className="h-full w-full" src={search} alt={""}/>
                    }
                </div>
                <input
                    ref={searchInputRef}
                    className="outline-none border-2 border-gray-200 rounded-lg text-xl pl-8 bg-gray-200 h-full w-11/12 focus:bg-white focus:border-pink-600"
                    type="text"
                    placeholder={placeholder}
                    onKeyDown={({key}) => {
                        if (key === 'Enter') onSearch();
                        if (key === 'ESC') setFocus(false);
                    }}
                    onFocus={() => setFocus(true)}
                    onBlur={() => setFocus(false)}
                />
            </div>
            {focus?<SearchSuggestions/>:null}
        </div>
    )
}