import React from 'react';
import Result from "./Result";
import ErrorMessage from "./ErrorMessage";

export interface Principle {
    principleText: string,
    parentPrinciple: string,
    childPrinciple: string
    id: string,
    topic: string
}

export default function Results({principles}: { principles: Principle[] }) {
    return (
        <div className="container pt-4 mx-auto">
            {principles.length > 0 ?
                principles.map((principle) => {
                    return <Result key={principle.id} principle={principle}/>
                }) : <ErrorMessage message="No results"/>
            }
        </div>
    )
}