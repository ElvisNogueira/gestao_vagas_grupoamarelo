package br.com.timeamarelo.gestao_vagas;

public class AuthenticationException  extends RuntimeException {
    public AuthenticationException() {
        super("Username/password incorrect");
    }
}
