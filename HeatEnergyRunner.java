import java.util.Scanner;

public class HeatEnergyRunner {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);

        //Input for mass of water// 
        System.out.println("What do you want the mass of the water to be (grams)?");
        double mass = reader.nextDouble();

        //Input for INITIAL temperature of water, then making sure it is not lower than abs zero//
        System.out.println("What do you want the initial temperature of the water to be (celcius)?");
        double initTemp = reader.nextDouble();
        if(initTemp < -273.14)
            initTemp = -273.14;

        //Same as above, but for FINAL temperature//
        System.out.println("What do you want the final temperature of the water to be (celcius)?");
        double finalTemp = reader.nextDouble();
        if(finalTemp < -273.14)
            finalTemp = -273.14;

        //Setting the initial Phase//
        String initPhase = "liquid";
        if(initTemp < 0)
            initPhase = "solid";
        if(initTemp > 100)
            initPhase = "gas";
        
        //Setting the final Phase//
        String finalPhase = "liquid";
        if(finalTemp < 0)
            finalPhase = "solid";
        if(finalTemp > 100)
            finalPhase = "gas";
        
        //computer tells user the mass, initial temp, and final temp//
        System.out.println("Mass: " + mass + "g");
        System.out.println("Initial Temperature: " + initTemp + "C " + initPhase);
        System.out.println("Final Temperature: " + finalTemp + "C " + finalPhase);

        //testing if the change is endo/exo thermic//
        boolean endothermic = false;
        if(finalTemp > initTemp)
            endothermic = true;

        //setting the final heat energy//
        double heatEnergy = 0;
        //what happens if we start as a solid?//
        if(initPhase.equals("solid")){
            heatEnergy += tempChangeSolid(mass, initTemp, finalTemp, finalPhase, endothermic);
            if(!finalPhase.equals("solid")){
                heatEnergy += phaseChangeSolidLiquid(mass, endothermic);
                heatEnergy += tempChangeLiquid(mass, 0, finalTemp, finalPhase, endothermic);
            }
            if(finalPhase.equals("gas")){
                heatEnergy += phaseChangeLiquidGas(mass, endothermic);
                heatEnergy += tempChangeGas(mass, 100, finalTemp, finalPhase, endothermic);
            }
        }
        //what happens if we start as a gas?//
        if(initPhase.equals("gas")){
            heatEnergy += tempChangeGas(mass, initTemp, finalTemp, finalPhase, endothermic);
            if(!finalPhase.equals("gas")){
                heatEnergy += phaseChangeLiquidGas(mass, endothermic);
                heatEnergy += tempChangeLiquid(mass, 100, finalTemp, finalPhase, endothermic);
            }
            if(finalPhase.equals("solid")){
                heatEnergy += phaseChangeSolidLiquid(mass, endothermic);
                heatEnergy += tempChangeSolid(mass, 0, finalTemp, finalPhase, endothermic);
            }
        }
        //what happens if we start as a liquid?//
        if(initPhase.equals("liquid")){
            heatEnergy += tempChangeLiquid(mass, initTemp, finalTemp, finalPhase, endothermic);
            if(finalPhase.equals("gas")){
                heatEnergy += phaseChangeLiquidGas(mass, endothermic);
                heatEnergy += tempChangeGas(mass, 100, finalTemp, finalPhase, endothermic);
            }
            if(finalPhase.equals("solid")){
                heatEnergy += phaseChangeSolidLiquid(mass, endothermic);
                heatEnergy += tempChangeSolid(mass, 0, finalTemp, finalPhase, endothermic);
            }
        }
        //prints final message//
        System.out.println("Total Heat Energy: " + round(heatEnergy) + "kJ");    
    } //main ends//

    //TEMPERATURE CHANGE SOLID//
    public static double tempChangeSolid(double mass, double startTemp, double endTemp, String endPhase, boolean endothermic){
        if(!endPhase.equals("solid"))
            endTemp = 0;
        double energyChange = round(mass * 0.002108 * (endTemp - startTemp));
        if(endothermic)
            System.out.println("Heating (solid): " + energyChange + "kJ");
        else
            System.out.println("Cooling (solid): " + energyChange + "kJ");
        return energyChange;
    }
    //TEMPERATURE CHANGE LIQUID//
    public static double tempChangeLiquid(double mass, double startTemp, double endTemp, String endPhase, boolean endothermic){
        if(endPhase.equals("gas"))
            endTemp = 100;
        if(endPhase.equals("solid"))
            endTemp = 0;
        double energyChange = round(mass * 0.004184 * (endTemp - startTemp));
        if(endothermic)
            System.out.println("Heating (liquid): " + energyChange + "kJ");
        else
            System.out.println("Cooling (liquid): " + energyChange + "kJ");
        return energyChange;
    }
    //TEMPERATURE CHANGE GAS//
    public static double tempChangeGas(double mass, double startTemp, double endTemp, String endPhase, boolean endothermic){
        if(!endPhase.equals("gas"))
            endTemp = 100;
        double energyChange = round(mass * 0.001996 * (endTemp - startTemp));
        if(endothermic)
            System.out.println("Heating (gas): " + energyChange + "kJ");
        else
            System.out.println("Cooling (gas): " + energyChange + "kJ");
        return energyChange;
    }
    //CONDENSATION/EVAPORATION//
    public static double phaseChangeLiquidGas(double mass, boolean endothermic){
        double energyChange;
        if(endothermic){
            energyChange = round(mass * 2.257);
            System.out.println("Evaporating: " + energyChange + "kJ");
        }
        else{
            energyChange = round(mass * -2.257);
            System.out.println("Condensating: " + energyChange + "kJ");
        }
        return energyChange;
    }
    //FREEZING/MELTING//
    public static double phaseChangeSolidLiquid(double mass, boolean endothermic){
        double energyChange;
        if(endothermic){
            energyChange = round(mass * 0.333);
            System.out.println("Melting: " + energyChange + "kJ");
        }
        else{
            energyChange = round(mass * -0.333);
            System.out.println("Freezing: " + energyChange + "kJ");
        }
        return energyChange;
    }  
    //rounding//
    public static double round(double x){
        x *= 1000;
        if(x>0)
            return (int)(x+0.5)/1000.0;
        else
            return (int)(x-0.5)/1000.0;
    }
}//support ends//