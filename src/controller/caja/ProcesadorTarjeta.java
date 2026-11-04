package controller.caja;

public class ProcesadorTarjeta {
    public boolean conectarPOS(double monto) {
        System.out.println("Enviando cobro de S/ " + monto + " al POS...");
        System.out.println("Transacción aprobada por el banco.");
        return true;
    }
}