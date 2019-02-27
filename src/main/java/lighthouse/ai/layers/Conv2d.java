package lighthouse.ai.layers;

import lighthouse.ai.Model;

public class Conv2d implements Layer{

    private Model model;

    public Conv2d(Model model){
        this.model = model;
    }

    @Override
    public double[][] calculate(double[][] in, int size){
        throw new IllegalArgumentException("Wrong Layer");
    }

    @Override
    public double[][] calculate(double[][] in, int height, int width){
        double[][] res = new double[in.length - (height - 1)][];
        for(int row = 0; row < res.length; row++){
            res[row] = new double[in[row].length - (width - 1)];
            for (int col = 0; col < res[row].length; col++){
                for (int i = 0 + row; i < height + row; i++){
                    for (int o = 0 + col; o < width + col; i++){
                        res[row][col] += in[i][o] * model.getNextWeight();
                    }
                }
            }
        }
        return res;
    }

}