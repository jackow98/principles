import React, {useState} from "react";
import SearchBar from "./ui components/search/searchBar/SearchBar";
import principlesApi from "../apis/principlesApi";
import Results from "./ui components/results/Results";

export default function SearchFeature() {

    const [loading, setLoading] = useState(false);

    const handleSubmit = async () => {
        setLoading(true);
        const res = await principlesApi.get(`/${"Jack"}`);
        console.log(res);
        setLoading(false);
    };

    const principles = [{
        "text": "To succeed, we must first know who we are and who we want to be, then, become that person",
        "parentPrinciple": "1",
        "childPrinciple": "2",
        "id": "1.1",
        "topic": "Education"
    }, {
        "text": "All decisions will pose, at first glance, a risky option and an easy option. Upon closer inspection both have the potential to offer good and bad. It is up to us to realistically weigh up the positive and negative effects of both.",
        "parentPrinciple": "1",
        "childPrinciple": "2",
        "id": "3.4",
        "topic": "Life"
    }
    ];

    return (
        <div className="">
            <SearchBar
                onSearch={() => handleSubmit()}
                placeholder={"Search all principles"}
                loading={loading}
            />
            <Results principles={principles}/>
        </div>
    )
}