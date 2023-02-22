import React, { useEffect, useState } from 'react';
import { Button, Col, Container, Form, Row } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { useUser } from '../UserProvider';
import { useLocalState } from '../util/useLocalStorage';

const Login = () => {

    const navigate = useNavigate();
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const user = useUser();

    useEffect(() => {
        if (user.jwt != null) {
            navigate("/dashboard");
        }
    }, [user.jwt]);

    function sendLoginRequest() {

        const reqBody = {
            "username": username,
            "password": password,
        }
        fetch('api/auth/login', {
            headers: {
                "Content-Type": "application/json",
            },
            method: "post",
            body: JSON.stringify(reqBody)
        })
            .then((response) => {
                if (response.status === 200)
                    return Promise.all([response.json(), response.headers]);
                else
                    return Promise.reject("Invalid login attempt!");
            })
            .then(([body, headers]) => {
                user.setJwt(String(headers.get("authorization")));
            }).catch((message) => {
                alert(message);
            });
    }


    return (
        <>
            <Container className="mt-5 ">
                <Row className="justify-content-center">
                    <Col md="8" lg="6">
                        <Form.Group className="mb-3" controlId="username">
                            <Form.Label className="fs-4">Username</Form.Label>
                            <Form.Control
                                type="email"
                                placeholder="joe@gmail.com"
                                size="lg"
                                value={username}
                                onChange={(event) => setUsername(event.target.value)} />
                        </Form.Group>
                    </Col>
                </Row>
                <Row className="justify-content-center">
                    <Col md="8" lg="6">
                        <Form.Group className="mb-3 " controlId="password">
                            <Form.Label className="fs-4">Password</Form.Label>
                            <Form.Control
                                type="password"
                                placeholder="Type in Your Password!"
                                size="lg"
                                value={password}
                                onChange={(event) => setPassword(event.target.value)} />
                        </Form.Group>
                    </Col>
                </Row>
                <Row className="justify-content-center">
                    <Col
                        md="8"
                        lg="6"
                        className="mt-2 d-flex flex-column gap-5 flex-md-row justify-content-md-between">
                        <Button
                            id="submit"
                            type="button"
                            size="lg"
                            onClick={() => sendLoginRequest()}>
                            Login
                        </Button>
                        <Button
                            variant="secondary"
                            type="button"
                            size="lg"
                            onClick={() => { navigate("/") }}>
                            Exit
                        </Button>
                    </Col>
                </Row>
            </Container>
        </>
    );
};

export default Login;