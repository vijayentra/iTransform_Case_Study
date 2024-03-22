import React, { Component } from "react";
import { Button, Form, FormGroup, Label, Input } from "reactstrap";
import {
  FacebookLoginButton,
  GoogleLoginButton,
} from "react-social-login-buttons";
import { GoogleLogin } from "react-google-login";
import { FacebookProvider, LoginButton } from "react-facebook-login";

export default class UserLogin extends Component {
  render() {
    return (
      <div className="App">
        <img className="logo" alt="Business view - Reports" />
        <form className="form" onSubmit={this.handleSubmit}>
          <div className="input-group">
            <label htmlFor="email">Email</label>
            <input type="email" name="email" placeholder="nome@email.com.br" />
          </div>
          <div className="input-group">
            <label htmlFor="password">Senha</label>
            <input type="password" name="password" />
          </div>
          <button className="primary">ENTRAR</button>
        </form>
        <button className="secondary" onClick={this.handleClick}>
          Criar uma nova conta
        </button>
      </div>
    );
  }
}
