import React, { useEffect, useState } from 'react';
import { Navigate } from 'react-router-dom';
import ajax from '../Services/fetchService';
import { useUser } from '../UserProvider';
import { useLocalState } from '../util/useLocalStorage';

const PrivateRoute = (props) => {
    const [isLoading, setIsLoading] = useState(true);
    const [isValid, setIsValid] = useState(null);
    const user = useUser();
    const { children } = props;

    if (user) {
        ajax(`/api/auth/validate?token=${user.jwt}`, "get", user.jwt).then(
            (isValid) => {
                setIsValid(isValid);
                setIsLoading(false);
            }
        );
    } else {
        return <Navigate to="/login" />;
    }

    return isLoading ? (
        <div>Loading...</div>
    ) : isValid === true ? (
        children
    ) : (
        <Navigate to="/login" />
    );
};

export default PrivateRoute;