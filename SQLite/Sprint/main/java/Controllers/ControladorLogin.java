package Controllers;

import Interfaces.LoginIterface;

public class ControladorLogin implements LoginIterface.Controlador {

    private final LoginIterface.View view;

    public ControladorLogin(LoginIterface.View view) {
        this.view = view;
    }

    @Override
    public Boolean validarLogin(String editText, String indicador) {
        if (indicador.equals("usuario")) {
            if (editText.trim().isEmpty()) {
                view.validarResultado(indicador, "Los campos no pueden estar vacíos");
                return false;
            } else if (editText.trim().length() <= 4) {
                view.validarResultado(indicador, "Los campos deben ser mayor o igual a 5");
                return false;
            }
        } else if (indicador.equals("password")) {
            if (editText.trim().isEmpty()) {
                view.validarResultado(indicador, "Los campos no pueden estar vacíos");
                return false;
            } else if (editText.trim().length() <= 4) {
                view.validarResultado(indicador, "Los campos deben ser mayor o igual a 5");
                return false;
            }
        }

        return true;
    }

    @Override
    public Boolean usuarioPermitido(String usuario, String password) {
        if ("prueba".equals(usuario) && "12345".equals(password)) {
            view.usuarioAutorizado(true);
            return true;
        } else {
            view.usuarioAutorizado(false);
            return false;
        }
    }
}
