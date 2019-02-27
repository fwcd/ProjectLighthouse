package lighthouse.ai;

public class AIMain {

    Model[] population;

    public AIMain(int pop){
        this.population = new Model[pop];
        for (int i = 0; i < pop; i++){
            Model m = new Model();
            m.addConv(3, 3);
            m.addConv(2, 1);
            m.addConv(1, 2);
            m.addDense(3);
            m.addDense(2);
            m.addDense(1);
            population[i] = m;
        }
    }
    
}