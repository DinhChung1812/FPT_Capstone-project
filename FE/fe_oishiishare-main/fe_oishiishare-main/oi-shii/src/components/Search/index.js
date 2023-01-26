import React, { useState } from 'react';
import PropTypes from 'prop-types';
import { useRef } from 'react';

SearchFilters.propTypes = {
    onSubmit: PropTypes.func,
};

SearchFilters.defaultProps = {
    onSubmit: null,
};

function SearchFilters(props) {
    const { onSubmit } = props;
    const [searchTerm, setSearchTerm] = useState('');
    const typingTimeoutRef = useRef(null);

    function handleSearchTermChange(e) {
        const value = e.target.value;
        setSearchTerm(value);

        if (!onSubmit) return;

        // SET -- 100 -- CLEAR, SET -- 300 --> SUBMIT
        // SET -- 300 --> SUBMIT
        if (typingTimeoutRef.current) {
            clearTimeout(typingTimeoutRef.current);
        };

        typingTimeoutRef.current = setTimeout(() => {
            const formValues = {
                searchTerm: value,
            };
            onSubmit(formValues);
        }, 300);
    }

    return (
        <form>
            <input
                className='input-search'
                type="text"
                placeholder='Search'
                value={searchTerm}
                onChange={handleSearchTermChange}
            />
        </form>
    );
}

export default SearchFilters;