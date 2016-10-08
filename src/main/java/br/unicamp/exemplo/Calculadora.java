package br.unicamp.exemplo;

public class Calculadora {
	private int result;

    public void add(int arg1, int arg2) {
        result = arg1 + arg2;
    }
    
    public void multiply(int arg1, int arg2) {
        result = arg1 * arg2;
    }


    public int getResult() {
        return result;
    }

	public void divide(int arg1, int arg2) {
		result = arg1 / arg2;
	}

}
