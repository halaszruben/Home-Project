import { useEffect, useState, } from 'react';
import jwt_decode from "jwt-decode";
import { Route, Routes } from 'react-router-dom';
import './App.css';
import AssignmentView from './AssignmentView';
import Dashboard from './Dashboard';
import Homepage from './Homepage';
import Login from './Login';
import PrivateRoute from './PrivateRoute';
import { useLocalState } from './util/useLocalStorage';
import 'bootstrap/dist/css/bootstrap.min.css';
import CodeReviewerDashboard from './CodeReviewerDashboard';
import CodeReviewerAssignmentView from './CodeReviewAssignmentView';
import { useUser } from './UserProvider';

function App() {


  console.log("Hello");

  const reqBody = {
    username: "ruben",
    password: "asdasd",
  };

  fetch("/api/auth/login", {
    headers: {
      "Content-Type": "application/json",
    },
    method: "post",
    body: JSON.stringify(reqBody),
  })
    .then((response) => Promise.all([response.json(), response.headers]))
    .then(([body, headers]) => {
      const authValue = headers.get("authorization");
      console.log(authValue);
      console.log(body);
    });

  console.log("Hello");

  //const [jwt, setJwt] = useLocalState("", "jwt");
  const [roles, setRoles] = useState([]);
  const user = useUser();

  useEffect(() => {
    setRoles(getRolesFromJWT());
  }, [user.jwt])

  function getRolesFromJWT() {
    if (user.jwt) {
      const decodedJwt = jwt_decode(user.jwt);
      return decodedJwt.authorities;
    }
    return [];
  }

  return (
    <Routes>
      <Route path="/dashboard" element={
        roles.find((role) => role === "ROLE_CODE_REVIEWER") ? (
          <PrivateRoute >
            <CodeReviewerDashboard />
          </PrivateRoute>
        ) : (
          <PrivateRoute >
            <Dashboard />
          </PrivateRoute>
        )
      }
      />
      < Route path="/assignments/:assignmentId"
        element={
          roles.find((role) => role === "ROLE_CODE_REVIEWER") ? (
            < PrivateRoute >
              <CodeReviewerAssignmentView />
            </PrivateRoute >
          ) : (
            < PrivateRoute >
              <AssignmentView />
            </PrivateRoute >)
        }
      />
      < Route path="/login" element={< Login />} />
      < Route path="/" element={< Homepage />} />
    </Routes >

  );
}

export default App;
