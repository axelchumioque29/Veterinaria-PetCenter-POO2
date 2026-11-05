package model.caja;

public class PruebaCaja {
    public void ejecutarTestAperturaYCierre() {
        System.out.println("--- pruebas unitaria de caja ---");

        // 1. Instanciamos datos controlados
        Cajero admin = new Cajero("C01", "Admin de Prueba");
        CajaDiaria cajaTest = new CajaDiaria(admin, 150.0);

        // 2. Verificamos que inicie abierta y con el saldo correcto
        if (cajaTest.getEstado() == EstadoCaja.ABIERTA && cajaTest.getSaldoInicial() == 150.0) {
            System.out.println(" Test 1 correcto :Apertura correcta.");
        } else {
            System.err.println("test 1 incorrecto: Error en estado o saldo de apertura.");
        }

        // 3. Probamos el cierre
        cajaTest.cerrarCaja();
        if (cajaTest.getEstado() == EstadoCaja.CERRADA) {
            System.out.println("Test 2 correcto: Cierre de caja correcto.");
        } else {
            System.err.println("Test 2 correcto: La caja no cambio su estado a CERRADA.");
        }
    }
}