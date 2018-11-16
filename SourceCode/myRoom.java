
/**
 * @file myRoom.java
 * @author changkaiyan (changkaiyan@std.uestc.edu.cn)
 * @brief Multi-central senser caculation
 * @version 0.2
 * @date 2018-11-14
 *
 * @copyright Copyright 2018 Kaiyan Chang, Kaiyuan Tian, Ruilin Chen
 *
 */
public class myRoom{
    //Person is in (7,5) (14,14)
    private char[][] room={
            {'s',' ','|',' ',' ','|',' ','|',' ',' ','|',' ','|',' ',' ','|',' ','|',' ',' '},
            {' ',' ','|','s',' ','|',' ','|',' ','s','|',' ','|','s',' ','|','s','|','s',' '},
            {'-','-','|',' ',' ','|','s','|',' ',' ','|','s','|',' ',' ','|',' ','|',' ',' '},
            {' ','s','|',' ',' ','|',' ','|',' ',' ','|',' ','|',' ',' ','|',' ','|',' ',' '},
            {' ',' ','|',' ',' ','|',' ','|',' ',' ','|','-','|','-','-','|','-','|','-','-'},
            {'-','-','|','-','|','-','|','-','-','-','|',' ','|',' ',' ','|','s','|',' ',' '},
            {' ',' ','|','s','|',' ','|',' ',' ',' ','|','s','|',' ',' ','|',' ','|',' ','s'},
            {' ',' ','|',' ','|','p','|',' ',' ',' ','|',' ','|',' ','s','|',' ','|',' ',' '},
            {' ','s','|',' ','|',' ','|',' ','s',' ','|',' ','|',' ',' ','|',' ','|',' ',' '},
            {' ',' ','|',' ','|',' ','|',' ',' ',' ','|','-','|','-','-','|','-','|','-','-'},
            {'s',' ','|',' ',' ','|',' ',' ',' ',' ','|','s','|','s',' ','|','s','|',' ',' '},
            {' ',' ','|','s',' ','|',' ',' ',' ','s','|',' ','|',' ',' ','|',' ','|',' ','s'},
            {'-','-','|',' ',' ','|',' ',' ',' ',' ','|',' ','|',' ',' ','|',' ','|',' ',' '},
            {' ','s','|',' ',' ','|',' ',' ',' ',' ','|','-','|','-','-','|','-','|','-','-'},
            {' ',' ','|',' ',' ','|',' ',' ',' ',' ','|','s','|',' ','p','|',' ','|',' ','s'},
            {'-','-','|','-','|','-','|','-','-','-','|',' ','|',' ',' ','|','s','|',' ',' '},
            {' ','s','|','s','|','s','|',' ','s',' ','|','-','|','-','-','|','-','|','-','-'},
            {' ',' ','|',' ','|',' ','|',' ',' ',' ','|','s','|',' ','s','|','s','|','s',' '},

    };
    private Senser[] roomsenser=new Senser[64];
    private int sensercount=0;
    public static void main(String[] args){
        myRoom mr=new myRoom();
        mr.showRoom();
        mr.setSenser();
        mr.getRoomTemperature();

    }
    public void showRoom()
    {
        for(int i=0;i<room.length;++i)
        {
            for(int j=0;j<room[i].length;++j)
            {
                System.out.print(room[i][j]);
            }
            System.out.println(' ');
        }
    }
    public void setSenser()
    {
        for(int i=0;i<room.length;++i)
        {
            for(int j=0;j<room[i].length;++j)
            {
                if(room[i][j]=='s')
                {
                    roomsenser[sensercount]=new Senser(i,j);
                    sensercount++;
                }
            }
        }
    }
    public void getRoomTemperature()
    {
        for(int i=0;i<sensercount;++i)
        {
            roomsenser[i].printData();
        }
    }
}
//I assume the room temperature is same in all rooms
class Senser{
    private int x;
    private int y;
    private double distance;
    private double setTemperature;
    //One-central-senser
    Senser(int x,int y,int person_x,int person_y,double centralSetTemperature ,double roomTemperature,double declineRate)
    {
        this.x=x;
        this.y=y;
        this.distance=Math.sqrt((x-person_x)*(x-person_x)+(y-person_y)*(y-person_y));
        setTemperature=roomTemperature+(centralSetTemperature-roomTemperature)*(1.0/(1+this.distance));
        if(roomTemperature<centralSetTemperature)//Often Winter
            setTemperature=Math.min(centralSetTemperature,roomTemperature+(centralSetTemperature-roomTemperature)*(1.0/(1+this.distance*this.distance))*declineRate);
        else//Often Summer
            setTemperature=Math.max(centralSetTemperature,roomTemperature+(centralSetTemperature-roomTemperature)*(1.0/(1+this.distance*this.distance))*declineRate);
    }
    //Multi-central-Senser
    Senser(int x,int y,double centralSetTemperature ,double roomTemperature,double declineRate)
    {
        int person_x=7;
        int person_y=5;
        int person2_x=14;
        int person2_y=14;
        this.x=x;
        this.y=y;
        this.distance=Math.min(Math.sqrt((x-person_x)*(x-person_x)+(y-person_y)*(y-person_y)),
                Math.sqrt((x-person2_x)*(x-person2_x)+(y-person2_y)*(y-person2_y)));
        setTemperature=roomTemperature+(centralSetTemperature-roomTemperature)*(1.0/(1+this.distance));
        if(roomTemperature<centralSetTemperature)//Often Winter
            setTemperature=Math.min(centralSetTemperature,roomTemperature+(centralSetTemperature-roomTemperature)*(1.0/(1+this.distance*this.distance))*declineRate);
        else//Often Summer
            setTemperature=Math.max(centralSetTemperature,roomTemperature+(centralSetTemperature-roomTemperature)*(1.0/(1+this.distance*this.distance))*declineRate);
    }
    //Multi-central-Senser
    Senser(int x,int y)
    {
        int person_x=7;
        int person_y=5;
        double centralSetTemperature = 26.0;
        double roomTemperature=35;
        this.x=x;
        this.y=y;
        double declineRate=35;//The recomment data
        this.distance=Math.sqrt((x-person_x)*(x-person_x)+(y-person_y)*(y-person_y));
        if(roomTemperature<centralSetTemperature)//Often Winter
            setTemperature=Math.min(centralSetTemperature,roomTemperature+(centralSetTemperature-roomTemperature)*(1.0/(1+this.distance*this.distance))*declineRate);
        else//Often Summer
            setTemperature=Math.max(centralSetTemperature,roomTemperature+(centralSetTemperature-roomTemperature)*(1.0/(1+this.distance*this.distance))*declineRate);
    }
    public void printData()
    {
        java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
        System.out.println("Loc : ("+x+","+y+")  Distance "+distance+" Setting temper:"+df.format(setTemperature));
    }
}
