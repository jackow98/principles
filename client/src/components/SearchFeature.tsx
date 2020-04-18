import React, {useState} from "react";
import SearchBar from "../ui components/SearchBar";

export default function SearchFeature() {

    const [loading, setLoading] = useState(false);

    const handleSubmit = () => {
        setLoading(true)
    };

    return (
        <SearchBar
            onSearch={() => handleSubmit()}
            placeholder={"Search all principles"}
            loading={loading}
        />
    )

}