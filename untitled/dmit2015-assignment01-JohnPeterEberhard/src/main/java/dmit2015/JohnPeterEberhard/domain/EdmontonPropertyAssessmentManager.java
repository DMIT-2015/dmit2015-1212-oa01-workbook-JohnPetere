package dmit2015.JohnPeterEberhard.domain;

import java.io.IOException;
import java.util.List;
import java.util.Map;


//import lombok.Getter;


public class EdmontonPropertyAssessmentManager {

    private List<EdmontonPropertyAssessment> propertyAssessmentList;
    private static EdmontonPropertyAssessmentManager instance;
    public static EdmontonPropertyAssessmentManager getInstance() throws IOException {
        if( instance ==null){
            synchronized (EdmontonPropertyAssessmentManager.class){
                if( instance == null){
                    instance = new EdmontonPropertyAssessmentManager();
                }
            }
        }
        return instance;
    }

    public EdmontonPropertyAssessmentManager() {

    }

/*
    TODO
    - List all the functions TODO at the window
    2) Create the READ CSV functionality
    3) Create test Class stuff;
 */

    public EdmontonPropertyAssessment findByAccountNumber(String accountNumber){

        return null;
    }
    public List<EdmontonPropertyAssessment> findByHouseNumberAndStreetName(String houseNumber, String streetName){
        return null;
    }

    public List<EdmontonPropertyAssessment> findWithenDIstance(Double Lattitude, Double Longitutude, Double meteres){
        return null;
    }
    public List<EdmontonPropertyAssessment> findDistintWard(String ward){
        return null;
    }
    public List<EdmontonPropertyAssessment> findDistinctNieghbourhood(){ return null} // THis needs to accept maps or something
    public List<String> findDistinctNeighborhoods(){
        // what does this even mean?
        return null;
    }
    public long totalAssessedValueByAssessmentClass(String assessmentClass){ return 0;}
    public long totalAssessedValueByAssessmentClassAndWard(String assessmentClass, String ward){return 0;}
    public long propertyCountByAssessmentClass(String assessmentClass){return 0;}
    public long propertyCountByAssessmentClassAndWard(String assessmentClass, String Ward){return 0;}
    public long minAssessdValueByAssessmentClassAndNeighbourhood(String assessmentCLass, String Neighbourhood){return 0;}
    public long maxAsssessValueByAssessmentClassAndNeighbourhood(String assessmentClass, String neighbourhood){return 0;}
    public double averageAssessedValueByAssessmentClassAndNeighbourhood(String assessmentClass, String neighbourhood){return -1;}
    public List<EdmontonPropertyAssessment> findByNeighbourhood(String Neighbourhood){return null;}
    public List<EdmontonPropertyAssessment> findByNeighbourhoodAndAssessedValueRange(String neighbourhood, double minAssessedVal, double maxAssessVal){
        return null;
    }





}
