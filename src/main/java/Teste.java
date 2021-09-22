public class Teste {
    public static void main(String[] args){
        Pessoa pessoa = new PF();
        new Teste().executarRegra(pessoa);
    }

    void executarRegra(Pessoa pessoa){
        pessoa.descontar();
    }
}

interface Pessoa {
    public void descontar();
}

class PF implements Pessoa{
    public void descontar(){
        System.out.println("PF");
    }
}

class PJ implements Pessoa{
    public void descontar(){
        System.out.println("PJ");
    }
}
