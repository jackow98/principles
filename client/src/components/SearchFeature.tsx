import React, {useEffect, useRef, useState} from "react";
import SearchBar from "./ui components/search/searchBar/SearchBar";
import principlesApi from "../apis/principlesApi";
import Results from "./ui components/results/Results";
import SearchSuggestions from "./ui components/search/searchSuggestions/SearchSuggestions";

function useOutsideAlerter(ref: React.RefObject<HTMLDivElement>, setSearchFocus: { (value: React.SetStateAction<boolean>): void; (arg0: boolean): void; }) {
    useEffect(() => {
        function handleClickOutside(event: { target: any; }) {
            if (ref.current && !ref.current.contains(event.target)) {
                setSearchFocus(false);
            }
        }

        // Bind the event listener
        document.addEventListener("mousedown", handleClickOutside);
        return () => {
            // Unbind the event listener on clean up
            document.removeEventListener("mousedown", handleClickOutside);
        };
    }, [ref]);
}

export default function SearchFeature() {

    const [loading, setLoading] = useState(false);
    const [results, setResults] = useState([]);
    const [searchTerm, setSearchTerm] = useState("");
    const [searchFocus, setSearchFocus] = useState(false);
    const [searchIntent, setSearchIntent] = useState(false);
    const searchInputRef = useRef<HTMLInputElement>(null);
    const searchSuggestionsRef = useRef<HTMLDivElement>(null);
    const isInitialMount = useRef(true);

    useEffect(() => {
        console.log(searchIntent);
        if (isInitialMount.current) {
            if (searchInputRef.current) {
                searchInputRef.current.focus();
                setSearchFocus(true)
            }
            isInitialMount.current = false;
        }

        const getSearchResults = async () => {
            if (searchIntent) {
                setSearchFocus(false);
                setLoading(true);
                const res = await principlesApi.get(`/?searchTerm=${searchTerm}`);
                setLoading(false);
                setResults(res.data);
            }
        };

        if(searchIntent){
            getSearchResults();
            setSearchIntent(false)
        }

    },[searchIntent]);


    const handleSubmit = async () => {
        setSearchIntent(true)
    };

    const handleSuggestionSearch = (value: string) => {
        setSearchTerm(value);
        setSearchIntent(true)
    };

    useOutsideAlerter(searchSuggestionsRef, setSearchFocus);

    return (
        <>
            <div className="mx-auto w-auto">
                <SearchBar
                    onSearch={() => handleSubmit()}
                    updateSearchTerm={(searchTerm: string) => setSearchTerm(searchTerm)}
                    placeholder={"Search all principles"}
                    loading={loading}
                    focus={searchFocus}
                    setFocus={setSearchFocus}
                    searchInputRef={searchInputRef}
                />
                {searchFocus ? <SearchSuggestions searchSuggestionsRef={searchSuggestionsRef}
                                                  updateSearchTerm={handleSuggestionSearch}/> : null}
            </div>

            {results ? <Results principles={results}/> : null}
        </>
    )
}