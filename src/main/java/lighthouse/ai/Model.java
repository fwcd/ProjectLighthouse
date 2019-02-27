package lighthouse.ai;

import java.util.ArrayList;
import java.util.Random;

import lighthouse.ai.layers.Conv2d;
import lighthouse.ai.layers.Dense;

public class Model{

    private Random r = new Random();
    private ArrayList<Double> weights = new ArrayList<>();
    private int weightCounter = 0;
    public Double fitness;
    private ArrayList<int[]> args = new ArrayList<>();
    private Dense dense;
    private Conv2d conv;

    public Model(){
        this.dense = new Dense(this);
        this.conv = new Conv2d(this);
    }

    public Model(ArrayList<Double> weights){
        this.weights = weights;
    }

    public double getNextWeight(){
        if (this.weights.size() <= weightCounter){
            this.weights.add(r.nextDouble());
        }
        return weights.get(weightCounter++);
    }

    public void resetCounter(){
        this.weightCounter = 0;
    }

    public void addDense(int size){
        int[] i = {size};
        this.args.add(i);
    }

    public void addConv(int height, int width){
        int[] i = {height, width};
        this.args.add(i);
    }

    public double feed(double[][] in){
        for (int [] arg : args){
            if (arg.length > 1){
                in = dense.calculate(in, arg[0]);
            }else{
                in = conv.calculate(in, arg[0], arg[1]);
            }
        }
        return in[0][0];
    }

    public ArrayList<Double> getWeights(){
        return weights;
    }

    public void setWeights(ArrayList<Double> weights){
        this.weights = weights;
    }

    public void mutateWeights(){
        for (int i = 0; i < weights.size(); i++){
            weights.set(i, weights.get(i) * r.nextGaussian());
        }
    }
    
}