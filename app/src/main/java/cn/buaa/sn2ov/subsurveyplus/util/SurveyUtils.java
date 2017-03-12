package cn.buaa.sn2ov.subsurveyplus.util;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Environment;
import android.widget.TextView;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.buaa.sn2ov.subsurveyplus.AppConstant;
import cn.buaa.sn2ov.subsurveyplus.AppContext;
import cn.buaa.sn2ov.subsurveyplus.db.RealmHelper;
import cn.buaa.sn2ov.subsurveyplus.model.response.user.UserItem;
import cn.buaa.sn2ov.subsurveyplus.model.table.TransRealm;

public class SurveyUtils {
	
	public static void onClickChangeArray(TextView tv, String[] array){
		for(int i=0;i<array.length;i++)
		{
			if(tv.getText().toString().equals(array[i])){
				tv.setText(array[(i+1)%array.length].toString());
				break;
			}
			//修改过潜在出错
			if(tv.getText().toString().equals(""))
				tv.setText(array[0].toString());
		}
	}
	
	public static void onClickChangeArray(TextView tv, int arr, Context context){
		Resources res =context.getResources();
		String[] array=res.getStringArray(arr);
		for(int i=0;i<array.length;i++)
		{
			if(tv.getText().toString().equals(array[i])){
				tv.setText(array[(i+1)%array.length].toString());
				break;
			}
		}
	}
	
//	public static void onClickUpdateWalkLine(TextView typeTv, TextView lineTv, UserEntity user){
//
//		switch(SurveyUtils.getSurveyTypeNo(typeTv.getText().toString(), AppConfig.wsTypeInfo)){
//
//		case AppConfig.WS_ON_TO_OFF_NO:
//			lineTv.setText(user.getWslStartGLoc()+"-"+user.getWslDireSToE()+"-"+user.getWslEndGLoc()+"-"+user.getWslDireEToS());
//			break;
//		case AppConfig.WS_TRANSF_NO:
//			lineTv.setText(user.getWslStartLineStartDire()+"-"+user.getWslEndLineStartDire()+"-"+user.getWslStartLineEndDire()+"-"+user.getWslEndLineEndDire());
//			break;
//		}
//	}
//
//	public static void onClickUpdateODLine(TextView typeTv, TextView lineTv, UserEntity user){
//		switch(SurveyUtils.getSurveyTypeNo(typeTv.getText().toString(), AppConfig.odTypeInfo)){
//		case AppConfig.OD_TRANSFER_NONE:
//			lineTv.setText(user.getStation()+"-"+user.getOdlOffStation1());
//			break;
//		case AppConfig.OD_TRANSFER_ONCE:
//			lineTv.setText(user.getStation()+"-"+user.getOdlOffStation1()+"-"+user.getOdlOffStation2());
//			break;
//		case AppConfig.OD_TRANSFER_TWICE:
//			lineTv.setText(user.getStation()+"-"+user.getOdlOffStation1()+"-"+user.getOdlOffStation2()+"-"+user.getOdlOffStation3());
//			break;
//		}
//	}
//
//	/**
//	 * @param tv
//	 * @param user
//	 * @param no 第no小组
//	 * @param transTodoNo 和计时页面的参数对应
//	 * @info 获取当前用户的走行预设路线
//	 */
//	public static void getWSLine(TextView tv, UserEntity user, int no, int transTodoNo){
//		String line="";
//		String start="";
//		String end="";
//		switch(SurveyUtils.getSurveyTypeNo(user.getWsType(), AppConfig.wsTypeInfo)){
//		case AppConfig.WS_ON_TO_OFF_NO:
//			switch((no+4)%4){
//			case 0:
//				start = user.getWslGLine()+user.getWslStartGLoc();
//				end = user.getWslTrainLine()+user.getWslDireSToE();
//				line = "①进站:"+start+"-"+end;
//				break;
//			case 1:
//				start = user.getWslTrainLine()+user.getWslDireSToE();
//				end = user.getWslGLine()+user.getWslEndGLoc();
//				line = "②出站:"+start+"-"+end;
//				break;
//			case 2:
//				start = user.getWslGLine()+user.getWslEndGLoc();
//				end = user.getWslTrainLine()+user.getWslDireEToS();
//				line = "③进站:"+start+"-"+end;
//				break;
//			case 3:
//				start = user.getWslTrainLine()+user.getWslDireEToS();
//				end = user.getWslGLine()+user.getWslStartGLoc();
//				line = "④出站:"+start+"-"+end;
//				break;
//			}
//			break;
//		case AppConfig.WS_TRANSF_NO:
//			switch((no+4)%4){
//			case 0:
//				start = user.getWslStartLineStartDire();
//				end = user.getWslEndLineStartDire();
//				line = "①"+start+"-"+end;
//				break;
//			case 1:
//				start = user.getWslEndLineStartDire();
//				end = user.getWslStartLineEndDire();
//				line = "②"+start+"-"+end;
//				break;
//			case 2:
//				start = user.getWslStartLineEndDire();
//				end = user.getWslEndLineEndDire();
//				line = "③"+start+"-"+end;
//				break;
//			case 3:
//				start = user.getWslEndLineEndDire();
//				end = user.getWslStartLineStartDire();
//				line = "④"+start+"-"+end;
//				break;
//			}
//			if(transTodoNo == 0)
//				line += "("+start+")";
//			else
//				line += "("+end+")";
//			break;
//		}
//		tv.setText(line);
//	}
//
//	/**
//	 * 获取走行调查路线信息
//	 * @param user
//	 * @param wsType
//     * @return
//     */
//	public static String getWSLineString(UserEntity user, int wsType){
//		String lineInfo = "";
//		switch(wsType){
//			case AppConfig.WS_ON_TO_OFF_NO:
//				lineInfo = "①进站:"+user.getWslGLine()+user.getWslStartGLoc()+"-"+user.getWslTrainLine()+user.getWslDireSToE()+"\n"
//						+"②出站:"+user.getWslTrainLine()+user.getWslDireSToE()+"-"+user.getWslGLine()+user.getWslEndGLoc()+"\n"
//						+"③进站:"+user.getWslGLine()+user.getWslEndGLoc()+"-"+user.getWslTrainLine()+user.getWslDireEToS()+"\n"
//						+"④出站:"+user.getWslTrainLine()+user.getWslDireEToS()+"-"+user.getWslGLine()+user.getWslStartGLoc();
//				break;
//			case AppConfig.WS_TRANSF_NO:
//				lineInfo = "①"+user.getWslStartLineStartDire()+"-"+user.getWslEndLineStartDire()+"\n"
//						+"②"+user.getWslEndLineStartDire()+"-"+user.getWslStartLineEndDire()+"\n"
//						+"③"+user.getWslStartLineEndDire()+"-"+user.getWslEndLineEndDire()+"\n"
//						+"④"+user.getWslEndLineEndDire()+"-"+user.getWslStartLineStartDire();
//				break;
//		}
//		return lineInfo;
//	}
//
//	public static String getODLineString(UserEntity user, int odType){
//		String lineInfo ="";
//		switch(odType){
//			case AppConfig.OD_TRANSFER_NONE:
//				lineInfo = user.getStation()+"-"+user.getOdlOffStation1();
//				break;
//			case AppConfig.OD_TRANSFER_ONCE:
//				lineInfo = user.getStation()+"-"+user.getOdlOffStation1()+"-"+user.getOdlOffStation2();
//				break;
//			case AppConfig.OD_TRANSFER_TWICE:
//				lineInfo = user.getStation()+"-"+user.getOdlOffStation1()+"-"+user.getOdlOffStation2()+"-"+user.getOdlOffStation3();
//				break;
//		}
//		return lineInfo;
//	}
//
//	/**
//	 * 修改走行路线信息顺序
//	 * @param user
//	 * @param wsType
//     */
//	public static void changeWSLine(UserEntity user,int wsType){
//		String tempDire,tempLine;
//		switch (wsType){
//			case AppConfig.WS_ON_TO_OFF_NO:
//				tempDire = user.getWslStartGLoc();
//				user.setWslStartGLoc(user.getWslDireSToE());
//				user.setWslDireSToE(user.getWslEndGLoc());
//				user.setWslEndGLoc(user.getWslDireEToS());
//				user.setWslDireEToS(tempDire);
//				tempLine = user.getWslGLine();
//				user.setWslGLine(user.getWslTrainLine());
//				user.setWslTrainLine(tempLine);
//				break;
//			case AppConfig.WS_TRANSFER_NO:
//				tempDire = user.getWslStartLineStartDire();
//				user.setWslStartLineStartDire(user.getWslEndLineStartDire());
//				user.setWslEndLineStartDire(user.getWslStartLineEndDire());
//				user.setWslStartLineEndDire(user.getWslEndLineEndDire());
//				user.setWslEndLineEndDire(tempDire);
//				tempLine = user.getWslStartLine();
//				user.setWslStartLine(user.getWslEndLine());
//				user.setWslEndLine(tempLine);
//				break;
//		}
//	}
//
//	/**
//	 * 修改OD路线信息显示顺序
//	 * @param user
//	 * @param odType
//     */
//	public static void changeODLine(UserEntity user,int odType){
//		String tempStation,tempLine;
//		switch (odType){
//			case AppConfig.OD_TRANSFER_NONE:
//				tempStation = user.getStation();
//				user.setStation(user.getOdlOffStation1());
//				user.setOdlOffStation1(tempStation);
//				user.setOdlDire1("");
//				break;
//			case AppConfig.OD_TRANSFER_ONCE:
//				tempStation = user.getStation();
//				user.setStation(user.getOdlOffStation2());
//				user.setOdlOffStation2(tempStation);
//
//				tempLine = user.getLine();
//				user.setLine(user.getOdlTransLine2());
//				user.setOdlTransLine2(tempLine);
//
//				user.setOdlDire1("");
//				user.setOdlDire2("");
//				break;
//			case AppConfig.OD_TRANSFER_TWICE:
//				tempStation = user.getStation();
//				user.setStation(user.getOdlOffStation3());
//				user.setOdlOffStation3(tempStation);
//				tempStation = user.getOdlOffStation1();
//				user.setOdlOffStation1(user.getOdlOffStation2());
//				user.setOdlOffStation2(tempStation);
//
//				tempLine = user.getLine();
//				user.setLine(user.getOdlTransLine3());
//				user.setOdlTransLine3(tempLine);
//
//				user.setOdlDire1("");
//				user.setOdlDire2("");
//				user.setOdlDire3("");
//				break;
//		}
//	}
//
//	public static void getSSLine(TextView tv, UserEntity user, int countStop){
//		String line = user.getSsFromLoc()+"-"+user.getSsToLoc()+"(第"+countStop+"班车)";
//		tv.setText(line);
//	}
//
//	/**
//	 * @param tv
//	 * @param user
//	 * @info 获取当前用户的走行预设路线
//	 */
//	public static void getODLine(TextView tv, UserEntity user, int no){
//		String line="";
//		switch(SurveyUtils.getSurveyTypeNo(user.getOdType(), AppConfig.odTypeInfo)){
//		case AppConfig.OD_TRANSFER_NONE:
//			line = user.getStation()+"-"+user.getOdlOffStation1();
//			break;
//		case AppConfig.OD_TRANSFER_ONCE:
//			switch(no){
//			case 0:
//				line = "①"+user.getStation()+"-"+user.getOdlOffStation1();
//				break;
//			case 1:
//				line = "②"+user.getOdlOffStation1()+"-"+user.getOdlOffStation2();
//				break;
//			}
//			break;
//		case AppConfig.OD_TRANSFER_TWICE:
//			switch(no){
//			case 0:
//				line = "①"+user.getStation()+"-"+user.getOdlOffStation1();
//				break;
//			case 1:
//				line = "②"+user.getOdlOffStation1()+"-"+user.getOdlOffStation2();
//				break;
//			case 2:
//				line = "③"+user.getOdlOffStation2()+"-"+user.getOdlOffStation3();
//			}
//			break;
//		}
//		tv.setText(line);
//	}
//
//
//	/**
//	 * @param surveyType
//	 * @param array
//	 * @return 返回调查类型 如：进站-出站0，换乘1
//	 */
//	public static int getSurveyTypeNo(String surveyType, String[] array){
//		int surveyTypeNo;
//		for(surveyTypeNo=0;surveyTypeNo<array.length;surveyTypeNo++){
//			if(array[surveyTypeNo].equals(surveyType))
//						break;
//		}
//		return surveyTypeNo;
//	}
//
//	public static Boolean fillUserInfoIntoEntity(Context context, BaseSurveyEntity surveyEntity, UserEntity user){
//		if(StringUtils.isEmpty(user.getName())||StringUtils.isEmpty(user.getDate())||StringUtils.isEmpty(user.getStation())
//		||StringUtils.isEmpty(user.getLine())||StringUtils.isEmpty(user.getWeekdayIf())){
//			return false;
//		}
//		surveyEntity.setName(user.getName());
//		surveyEntity.setDate(user.getDate());
//		surveyEntity.setStation(user.getStation().toString());
//		surveyEntity.setLine(user.getLine());
//		surveyEntity.setWeekdayIf(user.getWeekdayIf());
//		return true;
//	}
//
//	public static void fillTransferLineInfoEntity(WalkSurveyEntity surveyEntity,UserEntity user){
//		surveyEntity.setGetOnLine(user.getWsOnLine());
//		surveyEntity.setGetOnDire(user.getWsOnDire());
//		surveyEntity.setGetOffLine(user.getWsOffLine().toString());
//		surveyEntity.setGetOffDire(user.getWsOffDire());
//	}

	public static void exportToCSV(String surveyName, Context context){
		String fileName = surveyName + StringUtils.getCurDate() + "源数据.csv";
		String header[] = {"序号","dd"};
		List<?> list = null;
		RealmHelper realmHelper = new RealmHelper(context);
//		if(surveyName.equals("走行调查"))
//			c = WalkSurveyDataHelper.queryWalkSurveyData(context);
//		else if(surveyName.equals("留乘调查"))
//			c = StaySurveyDataHelper.queryStaySurveyData(context);
//		else if(surveyName.equals("OD调查"))
//			c = ODSurveyDataHelper.queryODSurveyData(context);
		if(surveyName.equals("换乘量调查")){
			header = new String[]{"序号","姓名"};
			list = realmHelper.findTransAllRecord();
		}
//		else if(surveyName.equals("反向乘车调查"))
//			c = ReverseSurveyDataHelper.queryRSSurveyData(context);

		FileUtils.ExportToCSV(list,header,fileName);
	}

	public static void delSurveyInfo(Class cls, Context context){
		String rowID = "zz";
		RealmHelper realmHelper = new RealmHelper(context);
		realmHelper.deleteRealmObjectAll(cls);
		if(cls == TransRealm.class){
			rowID = "tsRowID";
		}
		//"wsDatabaseRowID","ssRowID","odDatabaseID","rsRowID"
		Editor editor = context.getSharedPreferences(rowID, Context.MODE_WORLD_WRITEABLE).edit();
		editor.putInt(rowID,1);
		editor.commit();

	}

	/**
	 * 数据导出到Excel中
	 * @param surveyType
	 * @param context
	 * @throws IOException
     */
	public static void  ExportToExcel(int surveyType,Context context) throws IOException {
		ArrayList<HashMap<String ,String>> totalDataArr;
		ArrayList<HashMap<String ,String>> perDataArr;
		HashMap<String,String> headData;
		Cursor cursor = null;
		AppContext appContext = (AppContext)context.getApplicationContext();
		UserItem user = AccountHelper.getUser();
		AssetManager assetManager = context.getAssets();
		switch (surveyType){
//			case AppConfig.WALK_SURVEY:
//				//获取Excel表头表体数据源
//				headData = SurveyUtils.getHeadDataSourceForExcel(AppConfig.WALK_SURVEY,context,user);
//				perDataArr = SurveyUtils.getBodyDataSourceForExcel(AppConfig.WALK_SURVEY,context);
//				//获取处理过的表体数据
//				totalDataArr = SurveyUtils.getBodyDataForExcel(AppConfig.WALK_SURVEY,perDataArr);
//				//将数据填充到Excel
//				SurveyUtils.insertDataIntoExcel(AppConfig.WALK_SURVEY,headData,totalDataArr,assetManager);
//				break;
//			case AppConfig.STAY_SURVEY:
//				//获取Excel表头表体数据源
//				headData = SurveyUtils.getHeadDataSourceForExcel(AppConfig.STAY_SURVEY,context,user);
//				perDataArr = SurveyUtils.getBodyDataSourceForExcel(AppConfig.STAY_SURVEY,context);
//				//获取处理过的表体数据
////				totalDataArr = SurveyUtils.getBodyDataForExcel(AppConfig.STAY_SURVEY,perDataArr);
//				//将数据填充到Excel
//				SurveyUtils.insertDataIntoExcel(AppConfig.STAY_SURVEY,headData,perDataArr,assetManager);
//				break;
//			case AppConfig.OD_SURVEY:
//				//获取Excel表头表体数据源
//				headData = SurveyUtils.getHeadDataSourceForExcel(AppConfig.OD_SURVEY,context,user);
//				perDataArr = SurveyUtils.getBodyDataSourceForExcel(AppConfig.OD_SURVEY,context);
//				//获取处理过的表体数据
////				totalDataArr = SurveyUtils.getBodyDataForExcel(AppConfig.OD_SURVEY,perDataArr);
//				//将数据填充到Excel
//				SurveyUtils.insertDataIntoExcel(AppConfig.OD_SURVEY,headData,perDataArr,assetManager);
//				break;
			case AppConstant.TRANSFER_SURVEY:
				//获取Excel表头表体数据源
				headData = SurveyUtils.getHeadDataSourceForExcel(AppConstant.TRANSFER_SURVEY,context,user);
				perDataArr = SurveyUtils.getBodyDataSourceForExcel(AppConstant.TRANSFER_SURVEY,context);
				//获取处理过的表体数据
				totalDataArr = SurveyUtils.getBodyDataForExcel(AppConstant.TRANSFER_SURVEY,perDataArr);
				//将数据填充到Excel
				SurveyUtils.insertDataIntoExcel(AppConstant.TRANSFER_SURVEY,headData,totalDataArr,assetManager);
				break;
//			case AppConfig.REVERSE_SURVEY:
//				headData = SurveyUtils.getHeadDataSourceForExcel(AppConfig.REVERSE_SURVEY,context,user);
//				perDataArr = SurveyUtils.getBodyDataSourceForExcel(AppConfig.REVERSE_SURVEY,context);
//				totalDataArr = SurveyUtils.getBodyDataForExcel(AppConfig.REVERSE_SURVEY,perDataArr);
//				//将数据填充到Excel
//				SurveyUtils.insertDataIntoExcel(AppConfig.REVERSE_SURVEY,headData,totalDataArr,assetManager);
//				break;
		}
	}

	public static String getValue(XSSFCell xssfCell) {
		if(xssfCell!=null){
			if (xssfCell.getCellType() == xssfCell.CELL_TYPE_BOOLEAN) {
				return String.valueOf(xssfCell.getBooleanCellValue());
			} else if (xssfCell.getCellType() == xssfCell.CELL_TYPE_NUMERIC) {
				return String.valueOf(xssfCell.getNumericCellValue());
			} else {
				return String.valueOf(xssfCell.getStringCellValue());
			}
		}
		return "";
	}

	/**
	 * 获取导出Excel数据表体数据源
	 * @param surveyType
	 * @param context
     * @return
     */
	public static ArrayList<HashMap<String ,String>> getBodyDataSourceForExcel(int surveyType, Context context){
		ArrayList<HashMap<String ,String>> perDataArr = new ArrayList<HashMap<String, String>>();
		RealmHelper realmHelper = new RealmHelper(context);
		List<?> list = null;
		switch (surveyType){
//			case AppConfig.WALK_SURVEY:
//				cursor = WalkSurveyDataHelper.queryWalkSurveyData(context);
//				for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
//					WalkSurveyEntity temp = new WalkSurveyEntity();
//					String wsType = cursor.getString(cursor.getColumnIndex(WalkSurveyEntity.TWalkType));
//					String speed = cursor.getString(cursor.getColumnIndex(WalkSurveyEntity.TSpeed));
//					String age = cursor.getString(cursor.getColumnIndex(WalkSurveyEntity.TAge));
//					String bagage = cursor.getString(cursor.getColumnIndex(WalkSurveyEntity.TBagageType));
//					String walkTool = cursor.getString(cursor.getColumnIndex(WalkSurveyEntity.TWalkTool));
//					String telConcern = cursor.getString(cursor.getColumnIndex(WalkSurveyEntity.TTelConcern));
//					String sex = cursor.getString(cursor.getColumnIndex(WalkSurveyEntity.TSex));
//					String routeNo = cursor.getString(cursor.getColumnIndex(WalkSurveyEntity.TRounteNo));
//					String openDoorTime1 = cursor.getString(cursor.getColumnIndex(WalkSurveyEntity.TOpenDoorTime1));
//					String gotoPlatformTime = cursor.getString(cursor.getColumnIndex(WalkSurveyEntity.TGotoPlatformTime));
//					String arrivePlatformTime = cursor.getString(cursor.getColumnIndex(WalkSurveyEntity.TArrivePlatformTime));
//					String openDoorTime2 = cursor.getString(cursor.getColumnIndex(WalkSurveyEntity.TOpenDoorTime2));
//					String gogoutPlatformTime = cursor.getString(cursor.getColumnIndex(WalkSurveyEntity.TGooutPlatformTime));
//
//					HashMap<String ,String> map = new HashMap<String, String>();
//					map.put("wsType",wsType);
//					map.put("speed",speed);
//					map.put("age",age);
//					map.put("bagage",bagage);
//					map.put("walkTool",walkTool);
//					map.put("telConcern",telConcern);
//					map.put("sex",sex);
//					map.put("routeNo",routeNo);
//					map.put("openDoorTime1",openDoorTime1);
//					map.put("gotoPlatformTime",gotoPlatformTime);
//					map.put("arrivePlatformTime",arrivePlatformTime);
//					map.put("openDoorTime2",openDoorTime2);
//					map.put("gogoutPlatformTime",gogoutPlatformTime);
//					perDataArr.add(map);
//				}
//				break;
//			case AppConfig.STAY_SURVEY:
//				cursor = StaySurveyDataHelper.queryStaySurveyData(context);
//				for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
//					StaySurveyEntity temp = new StaySurveyEntity();
//					String ssType = cursor.getString(cursor.getColumnIndex(StaySurveyEntity.TSurveyType));
//					String sex = cursor.getString(cursor.getColumnIndex(StaySurveyEntity.TSex));
//					String age = cursor.getString(cursor.getColumnIndex(StaySurveyEntity.TAge));
//					String bagageType = cursor.getString(cursor.getColumnIndex(StaySurveyEntity.TBagageType));
//					String gotoPlatformTime = cursor.getString(cursor.getColumnIndex(StaySurveyEntity.TGotoPlatformTime));
//					String getoffTransferTime = cursor.getString(cursor.getColumnIndex(StaySurveyEntity.TGetoffTransferTime));
//					String arrivePlatformTime = cursor.getString(cursor.getColumnIndex(StaySurveyEntity.TArrivePlatformTime));
//					String orignLineNum =  cursor.getString(cursor.getColumnIndex(StaySurveyEntity.TOrignLineNum));
//					String trainStart1 = cursor.getString(cursor.getColumnIndex(StaySurveyEntity.TTrainStartNO1));
//					String trainStart2 = cursor.getString(cursor.getColumnIndex(StaySurveyEntity.TTrainStartNO2));
//					String trainStart3 = cursor.getString(cursor.getColumnIndex(StaySurveyEntity.TTrainStartNO3));
//					String trainStart4 = cursor.getString(cursor.getColumnIndex(StaySurveyEntity.TTrainStartNO4));
//					String trainStart5 = cursor.getString(cursor.getColumnIndex(StaySurveyEntity.TTrainStartNO5));
//					String trainStart6 = cursor.getString(cursor.getColumnIndex(StaySurveyEntity.TTrainStartNO6));
//					String getonNum1 = cursor.getString(cursor.getColumnIndex(StaySurveyEntity.TGetonNumNO1));
//					String getonNum2 = cursor.getString(cursor.getColumnIndex(StaySurveyEntity.TGetonNumNO2));
//					String getonNum3 = cursor.getString(cursor.getColumnIndex(StaySurveyEntity.TGetonNumNO3));
//					String getonNum4 = cursor.getString(cursor.getColumnIndex(StaySurveyEntity.TGetonNumNO4));
//					String getonNum5 = cursor.getString(cursor.getColumnIndex(StaySurveyEntity.TGetonNumNO5));
//					String getonNum6 = cursor.getString(cursor.getColumnIndex(StaySurveyEntity.TGetonNumNO6));
//					String getoffNum1 = cursor.getString(cursor.getColumnIndex(StaySurveyEntity.TGetoffNumNO1));
//					String getoffNum2 = cursor.getString(cursor.getColumnIndex(StaySurveyEntity.TGetoffNumNO2));
//					String getoffNum3 = cursor.getString(cursor.getColumnIndex(StaySurveyEntity.TGetoffNumNO3));
//					String getoffNum4 = cursor.getString(cursor.getColumnIndex(StaySurveyEntity.TGetoffNumNO4));
//					String getoffNum5 = cursor.getString(cursor.getColumnIndex(StaySurveyEntity.TGetoffNumNO5));
//					String getoffNum6 = cursor.getString(cursor.getColumnIndex(StaySurveyEntity.TGetoffNumNO6));
//
//					HashMap<String ,String> map = new HashMap<String, String>();
//					map.put("ssType",ssType);
//					map.put("sex",sex);
//					map.put("age",age);
//					map.put("bagageType",bagageType);
//					map.put("gotoPlatformTime",gotoPlatformTime);
//					map.put("getoffTransferTime",getoffTransferTime);
//					map.put("arrivePlatformTime",arrivePlatformTime);
//					map.put("orignLineNum",orignLineNum);
//					map.put("trainStart1",trainStart1);
//					map.put("trainStart2",trainStart2);
//					map.put("trainStart3",trainStart3);
//					map.put("trainStart4",trainStart4);
//					map.put("trainStart5",trainStart5);
//					map.put("trainStart6",trainStart6);
//					map.put("getonNum1",getonNum1);
//					map.put("getonNum2",getonNum2);
//					map.put("getonNum3",getonNum3);
//					map.put("getonNum4",getonNum4);
//					map.put("getonNum5",getonNum5);
//					map.put("getonNum6",getonNum6);
//					map.put("getoffNum1",getoffNum1);
//					map.put("getoffNum2",getoffNum2);
//					map.put("getoffNum3",getoffNum3);
//					map.put("getoffNum4",getoffNum4);
//					map.put("getoffNum5",getoffNum5);
//					map.put("getoffNum6",getoffNum6);
//					perDataArr.add(map);
//
//				}
//				break;
			case AppConstant.TRANSFER_SURVEY:
				list = realmHelper.findTransAllRecord();
				for(int i = 0;i<list.size();i++){
					TransRealm temp = (TransRealm)list.get(i);
					String surveyTime = temp.getSurveyTime();
					String count = temp.getCount();
					HashMap<String ,String> map = new HashMap<String, String>();
					map.put("surveyTime",surveyTime);
					map.put("count",count);
					perDataArr.add(map);
				}
				break;
//			case AppConfig.OD_SURVEY:
//				cursor = ODSurveyDataHelper.queryODSurveyData(context);
//				for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
//					ODSurveyEntity temp = new ODSurveyEntity();
//					String date = cursor.getString(cursor.getColumnIndex(ODSurveyEntity.TDate));
//					String name = cursor.getString(cursor.getColumnIndex(ODSurveyEntity.TName));
//					String INNo = cursor.getString(cursor.getColumnIndex(ODSurveyEntity.TIDNo));
//					String cardNum = cursor.getString(cursor.getColumnIndex(ODSurveyEntity.TCardNum));
//					String station = cursor.getString(cursor.getColumnIndex(ODSurveyEntity.TGetinStation));
//					String line = cursor.getString(cursor.getColumnIndex(ODSurveyEntity.TGetinStationLine));
//					String stationCount = cursor.getString(cursor.getColumnIndex(ODSurveyEntity.TStationCount));
//					String distance = cursor.getString(cursor.getColumnIndex(ODSurveyEntity.TDistanceTotal));
//					String transferCount = cursor.getString(cursor.getColumnIndex(ODSurveyEntity.TTransferCount));
//					String getinStationTime = cursor.getString(cursor.getColumnIndex(ODSurveyEntity.TGetinStationTime));
//					String arriveStationTime1 = cursor.getString(cursor.getColumnIndex(ODSurveyEntity.TArriveStationTime1));
//					String trainDire1 = cursor.getString(cursor.getColumnIndex(ODSurveyEntity.TTrainDire1));
//					String shift1 = cursor.getString(cursor.getColumnIndex(ODSurveyEntity.TShift1));
//					String trainStartTime1 = cursor.getString(cursor.getColumnIndex(ODSurveyEntity.TTrainStartingTime1));
//					String getoffStationTime1 = cursor.getString(cursor.getColumnIndex(ODSurveyEntity.TGetoffStationTime1));
//					String getOffStation1 = cursor.getString(cursor.getColumnIndex(ODSurveyEntity.TGetoffStation1));
//					String transferLine1 = cursor.getString(cursor.getColumnIndex(ODSurveyEntity.TTransferLine1));
//					String arriveStationTime2 = cursor.getString(cursor.getColumnIndex(ODSurveyEntity.TArriveStationTime2));
//					String trainDire2 = cursor.getString(cursor.getColumnIndex(ODSurveyEntity.TTrainDire2));
//					String shift2 = cursor.getString(cursor.getColumnIndex(ODSurveyEntity.TShift2));
//					String trainStartTime2 = cursor.getString(cursor.getColumnIndex(ODSurveyEntity.TTrainStartingTime2));
//					String getoffStationTime2 = cursor.getString(cursor.getColumnIndex(ODSurveyEntity.TGetoffStationTime2));
//					String getOffStation2 = cursor.getString(cursor.getColumnIndex(ODSurveyEntity.TGetoffStation2));
//					String transferLine2 = cursor.getString(cursor.getColumnIndex(ODSurveyEntity.TTransferLine2));
//					String arriveStationTime3 = cursor.getString(cursor.getColumnIndex(ODSurveyEntity.TArriveStationTime3));
//					String trainDire3 = cursor.getString(cursor.getColumnIndex(ODSurveyEntity.TTrainDire3));
//					String trainStartTime3 = cursor.getString(cursor.getColumnIndex(ODSurveyEntity.TTrainStartingTime3));
//					String shift3 = cursor.getString(cursor.getColumnIndex(ODSurveyEntity.TShift3));
//					String getoffStationTime3 = cursor.getString(cursor.getColumnIndex(ODSurveyEntity.TGetoffStationTime3));
//					String getOffStation3 = cursor.getString(cursor.getColumnIndex(ODSurveyEntity.TGetoffStation3));
//					String getoutStationTime = cursor.getString(cursor.getColumnIndex(ODSurveyEntity.TGetoutStationTime));
//
//					HashMap<String ,String> map = new HashMap<String, String>();
//					map.put("date",date);
//					map.put("name",name);
//					map.put("IDNo",INNo);
//					map.put("cardNum",cardNum);
//					map.put("station",station);
//					map.put("line",line);
//					map.put("stationCount",stationCount);
//					map.put("distance",distance);
//					map.put("transferCount",transferCount);
//					map.put("getinStationTime",getinStationTime);
//					map.put("arriveStationTime1",arriveStationTime1);
//					map.put("trainDire1",trainDire1);
//					map.put("shift1",shift1);
//					map.put("trainStartTime1",trainStartTime1);
//					map.put("getoffStationTime1",getoffStationTime1);
//					map.put("getOffStation1",getOffStation1);
//					map.put("transferLine1",transferLine1);
//
//					map.put("arriveStationTime2",arriveStationTime2);
//					map.put("trainDire2",trainDire2);
//					map.put("shift2",shift2);
//					map.put("trainStartTime2",trainStartTime2);
//					map.put("getoffStationTime2",getoffStationTime2);
//					map.put("getOffStation2",getOffStation2);
//					map.put("transferLine2",transferLine2);
//
//					map.put("arriveStationTime3",arriveStationTime3);
//					map.put("trainDire3",trainDire3);
//					map.put("shift3",shift3);
//					map.put("trainStartTime3",trainStartTime3);
//					map.put("getoffStationTime3",getoffStationTime3);
//					map.put("getOffStation3",getOffStation3);
//					map.put("getoutStationTime",getoutStationTime);
//
//					perDataArr.add(map);
//				}
//				break;
//			case AppConfig.REVERSE_SURVEY:
//				cursor = ReverseSurveyDataHelper.queryRSSurveyData(context);
//				for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
//					ReverseSurveyEntity temp = new ReverseSurveyEntity();
//					String count = cursor.getString(cursor.getColumnIndex(ReverseSurveyEntity.TCount));
//					HashMap<String ,String> map = new HashMap<String, String>();
//					map.put("count",count);
//					perDataArr.add(map);
//				}
//				break;
		}
		return perDataArr;

	}

	/**
	 * 获取Excel表头数据源
	 * @param surveyType
	 * @param context
     * @return
     */
	public static HashMap<String ,String> getHeadDataSourceForExcel(int surveyType, Context context, UserItem userEntity){
		HashMap<String ,String> map = new HashMap<String, String>();
		List<?> list = null;
		RealmHelper realmHelper = new RealmHelper(context);
		String name,date,station,timePeriod,type,carriageLoc;
		switch (surveyType){
//			case AppConfig.WALK_SURVEY:
//				cursor = WalkSurveyDataHelper.queryWalkSurveyData(context);
//				//14-14-14 和 路线
//				cursor.moveToFirst();
//				name = cursor.getString(cursor.getColumnIndex(WalkSurveyEntity.TName));
//				date = cursor.getString(cursor.getColumnIndex(WalkSurveyEntity.TDate));
//				station = cursor.getString(cursor.getColumnIndex(WalkSurveyEntity.TStation));
//
//				timePeriod = userEntity.getWsTime();
//				//进站-出站
//				type = userEntity.getWsType();
//				String wslDireEToS = userEntity.getWslDireEToS();
//				String wslDireSToE = userEntity.getWslDireSToE();
//				String wslStartGLoc = userEntity.getWslStartGLoc();
//				String wslEndGLoc = userEntity.getWslEndGLoc();
//				String wslGLine = userEntity.getWslGLine();
//				String wslTrainLine = userEntity.getWslTrainLine();
//				//换乘
//				String wslEndLine = userEntity.getWslEndLine();
//				String wslStartLine = userEntity.getWslStartLine();
//				String wslStartLineStartDire = userEntity.getWslStartLineStartDire();
//				String wslStartLineEndDire = userEntity.getWslStartLineEndDire();
//				String wslEndLineStartDire = userEntity.getWslEndLineStartDire();
//				String wslEndLineEndDire = userEntity.getWslEndLineEndDire();
//
//				map.put("name",name);
//				map.put("date",date);
//				map.put("station",station);
//				map.put("timePeriod",timePeriod);
//				map.put("type",type);
//				map.put("wslDireEToS",wslDireEToS);
//				map.put("wslDireSToE",wslDireSToE);
//				map.put("wslStartGLoc",wslStartGLoc);
//				map.put("wslEndGLoc",wslEndGLoc);
//				map.put("wslGLine",wslGLine);
//				map.put("wslTrainLine",wslTrainLine);
//
//				map.put("wslStartLine",wslStartLine);
//				map.put("wslEndLine",wslEndLine);
//				map.put("wslStartLineStartDire",wslStartLineStartDire);
//				map.put("wslStartLineEndDire",wslStartLineEndDire);
//				map.put("wslEndLineStartDire",wslEndLineStartDire);
//				map.put("wslEndLineEndDire",wslEndLineEndDire);
//
//				break;
//			case AppConfig.STAY_SURVEY:
//				cursor = StaySurveyDataHelper.queryStaySurveyData(context);
//				//14-14-14 和 路线
//				cursor.moveToFirst();
//				name = cursor.getString(cursor.getColumnIndex(StaySurveyEntity.TName));
//				date = cursor.getString(cursor.getColumnIndex(StaySurveyEntity.TDate));
//				station = cursor.getString(cursor.getColumnIndex(StaySurveyEntity.TStation));
//				timePeriod = cursor.getString(cursor.getColumnIndex(StaySurveyEntity.TTravelTime));
//				String isWeekday = cursor.getString(cursor.getColumnIndex(StaySurveyEntity.TWeekDayIF));
//				String gotoPlatformLoc = cursor.getString(cursor.getColumnIndex(StaySurveyEntity.TGotoPlatformLoc));
//				String transferLoc = cursor.getString(cursor.getColumnIndex(StaySurveyEntity.TTransferLoc));
//				String platformLoc = cursor.getString(cursor.getColumnIndex(StaySurveyEntity.TPlatformLoc));
//				carriageLoc = cursor.getString(cursor.getColumnIndex(StaySurveyEntity.TCarriageLoc));
//				map.put("name",name);
//				map.put("date",date);
//				map.put("station",station);
//				map.put("timePeriod",timePeriod);
//				map.put("isWeekday",isWeekday);
//				map.put("gotoPlatformLoc",gotoPlatformLoc);
//				map.put("transferLoc",transferLoc);
//				map.put("platformLoc",platformLoc);
//				map.put("carriageLoc",carriageLoc);
//				break;
			case AppConstant.TRANSFER_SURVEY:
				list = realmHelper.findTransAllRecord();
				for(int i = 0;i<list.size();i++){
					TransRealm temp = (TransRealm)list.get(i);
					map.put("name",temp.getName());
					map.put("date",temp.getDate());
					map.put("station",temp.getStation());
					map.put("dire",temp.getDire());
					map.put("timePeriod",temp.getTimePeriod());
				}
				break;
//			case AppConfig.OD_SURVEY:
//				cursor = ODSurveyDataHelper.queryODSurveyData(context);
//				String IDNo,cardNum,transferCount,srcStation,desStation;
//				cursor.moveToFirst();
//				name = cursor.getString(cursor.getColumnIndex(ODSurveyEntity.TName));
//				date = cursor.getString(cursor.getColumnIndex(ODSurveyEntity.TDate));
//				IDNo = cursor.getString(cursor.getColumnIndex(ODSurveyEntity.TIDNo));
//				cardNum = cursor.getString(cursor.getColumnIndex(ODSurveyEntity.TCardNum));
//				srcStation = cursor.getString(cursor.getColumnIndex(ODSurveyEntity.TGetinStation));
//
//				transferCount = cursor.getString(cursor.getColumnIndex(ODSurveyEntity.TTransferCount));
//				if(transferCount.equals("0"))
//					desStation = cursor.getString(cursor.getColumnIndex(ODSurveyEntity.TGetoffStation1));
//				else if(transferCount.equals("1"))
//					desStation = cursor.getString(cursor.getColumnIndex(ODSurveyEntity.TGetoffStation2));
//				else
//					desStation = cursor.getString(cursor.getColumnIndex(ODSurveyEntity.TGetoffStation3));
//
//				map.put("name",name);
//				map.put("date",date);
//				map.put("IDNo",IDNo);
//				map.put("cardNum",cardNum);
//				map.put("srcStation",srcStation);
//				map.put("desStation",desStation);
//				map.put("transferCount",transferCount);
//				break;
//			case AppConfig.REVERSE_SURVEY:
//				cursor = ReverseSurveyDataHelper.queryRSSurveyData(context);
//				String surveyTime;
//				cursor.moveToFirst();
//				date = cursor.getString(cursor.getColumnIndex(ReverseSurveyEntity.TDate));
//				surveyTime = cursor.getString(cursor.getColumnIndex(ReverseSurveyEntity.TSurveyTime));
//				name = cursor.getString(cursor.getColumnIndex(ReverseSurveyEntity.TName));
//				station = cursor.getString(cursor.getColumnIndex(ReverseSurveyEntity.TStation));
//				dire = cursor.getString(cursor.getColumnIndex(ReverseSurveyEntity.TDire));
//				carriageLoc = userEntity.getRsCarriageLoc();
//				type = userEntity.getRsType();
//				timePeriod = userEntity.getRsTimePeriod();
//
//				map.put("type",type);
//				map.put("date",date);
//				map.put("surveyTime",surveyTime);
//				map.put("name",name);
//				map.put("station",station);
//				map.put("carriageLoc",carriageLoc);
//				map.put("dire",dire);
//				map.put("timePeriod",timePeriod);
//				break;
		}
		return map;
	}

	/**
	 * 获取Excel表体显示数据
	 * @param surveyType
	 * @param perDataArr
	 * @return
	 */
	public static ArrayList<HashMap<String ,String>> getBodyDataForExcel(int surveyType, ArrayList<HashMap<String ,String>> perDataArr){
		HashMap<String ,String> map = new HashMap<String, String>();
		ArrayList<HashMap<String ,String>> totalDataArr = new ArrayList<HashMap<String, String>>();
//		RealmHelper realmHelper = new RealmHelper(context);
		switch (surveyType){
//			case AppConfig.WALK_SURVEY:
//				int line1Count=0,line2Count=0,line3Count=0,line4Count=0;
//				for(HashMap<String ,String> data : perDataArr){
//					String wsType = data.get("wsType");
//					String speed = data.get("speed");
//					String age = data.get("age");
//					String bagage = data.get("bagage");
//					String walkTool = data.get("walkTool");
//					String sex = data.get("sex");
//					String telConcern = data.get("telConcern");
//					int routeNo = Integer.parseInt(data.get("routeNo"));
//					String openDoorTime1 = data.get("openDoorTime1");
//					String gotoPlatformTime = data.get("gotoPlatformTime");
//					String arrivePlatformTime = data.get("arrivePlatformTime");
//					String openDoorTime2 = data.get("openDoorTime2");
//					String gogoutPlatformTime = data.get("gogoutPlatformTime");
//					map.put("speed",speed);
//					map.put("age",age);
//					map.put("bagage",bagage);
//					map.put("walkTool",walkTool);
//					map.put("telConcern",telConcern);
//					map.put("sex",sex);
//					map.put("routeNo",data.get("routeNo"));
//					if(wsType.equals("进站-出站")){
//						switch (routeNo){
//							case 1:
//								map.put("gotoPlatformTime",gotoPlatformTime);
//								map.put("arrivePlatformTime",arrivePlatformTime);
//								map.put("openDoorTime2",openDoorTime2);
//								map.put("line1Count",line1Count+"");
//								line1Count++;
//								break;
//							case 2:
//								map.put("openDoorTime1",openDoorTime1);
//								map.put("gogoutPlatformTime",gogoutPlatformTime);
//								map.put("line2Count",line2Count+"");
//								line2Count++;
//								break;
//							case 3:
//								map.put("gotoPlatformTime",gotoPlatformTime);
//								map.put("arrivePlatformTime",arrivePlatformTime);
//								map.put("openDoorTime2",openDoorTime2);
//								map.put("line3Count",line3Count+"");
//								line3Count++;
//								break;
//							case 4:
//								map.put("openDoorTime1",openDoorTime1);
//								map.put("gogoutPlatformTime",gogoutPlatformTime);
//								map.put("line4Count",line4Count+"");
//								line4Count++;
//								break;
//						}
//					}else if(wsType.equals("换乘")){
//						map.put("openDoorTime1",openDoorTime1);
//						map.put("arrivePlatformTime",arrivePlatformTime);
//						map.put("openDoorTime2",openDoorTime2);
//						switch (routeNo){
//							case 1:
//								map.put("line1Count",line1Count+"");
//								line1Count++;
//								break;
//							case 2:
//								map.put("line2Count",line2Count+"");
//								line2Count++;
//								break;
//							case 3:
//								map.put("line3Count",line3Count+"");
//								line3Count++;
//								break;
//							case 4:
//								map.put("line4Count",line4Count+"");
//								line4Count++;
//								break;
//						}
//					}
//
//					totalDataArr.add(map);
//					map = new HashMap<String, String>();
//				}
//				break;
//			case AppConfig.STAY_SURVEY:
//				break;
			case AppConstant.TRANSFER_SURVEY:
				//next标识是否下一组，因为数据都是连续的，所以可以用此法
				int sum = 0,next = 0;
				String lastHour = perDataArr.get(0).get("surveyTime").substring(0,2);
				String lastMinute = perDataArr.get(0).get("surveyTime").substring(3,5);
				for(HashMap<String ,String> data:perDataArr){
					String surveyTime = data.get("surveyTime");
					String count = data.get("count");
					//09:00:00-09:05:00
					String hour = surveyTime.substring(0,2);
					String minute = surveyTime.substring(3,5);
					if(lastHour.equals(hour)){
						//根据lastMinute 和minute 判断是否next
						//5min粒度 0-4/5-9/10-14/15-19
						if((Integer.parseInt(minute)/5 != Integer.parseInt(lastMinute)/5)||(data==perDataArr.get(perDataArr.size()-1))){
							next = 1;
						}else{
							next = 0;
						}
					}else{
						next = 1;
					}
					//next:0 即未开启新一组数据
					if(next == 0)
						sum += Integer.parseInt(count);
					else{
						if(data==perDataArr.get(perDataArr.size()-1))
							sum += Integer.parseInt(count);
						//记录数据
						//计算出时间
						String time;
						if(Integer.parseInt(lastMinute)/5 == 11)
							time = lastHour + ":" + Integer.parseInt(lastMinute)/5*5 + " - " + (Integer.parseInt(lastHour)+1) + ":00";
						else
							time = lastHour + ":" + Integer.parseInt(lastMinute)/5*5 + " - " + lastHour + ":" + (Integer.parseInt(lastMinute)/5+1)*5;
						map.put("surveyTime",time);
						map.put("count",sum+"");
						totalDataArr.add(map);
						map = new HashMap<String, String>();
						sum = Integer.parseInt(count);
						next = 0;
					}
					lastHour = hour;
					lastMinute = minute;
				}
				break;
//			case AppConfig.OD_SURVEY:
//				break;
//			case AppConfig.REVERSE_SURVEY:
//				for(HashMap<String ,String> data : perDataArr){
//					map.put("count",data.get("count"));
//					totalDataArr.add(map);
//					map = new HashMap<String, String>();
//				}
//				break;
		}
		return totalDataArr;
	}

	/**
	 * 根据表头数据和表体处理过的数据和模板，导出新的调查数据
	 * @param surveyType
	 * @param headData
	 * @param totalDataArr
	 * @throws IOException
     */
	public static void insertDataIntoExcel(int surveyType , HashMap<String,String> headData , ArrayList<HashMap<String ,String>> totalDataArr, AssetManager assetManager) throws IOException {
		String newFilePath,name,date,station,timePeriod,surveyName = "",dire,type,carriageLoc;
		File file = null,srcTemplateFile;
		InputStream assetData;
		FileInputStream fis = null;
		XSSFWorkbook workbook = null;
		XSSFSheet sheet = null;
		XSSFCell headCell = null;
		FileOutputStream fos = null;
		switch (surveyType){
//			case AppConfig.WALK_SURVEY:
//				name = headData.get("name");
//				date = headData.get("date");
//				station = headData.get("station");
//				timePeriod = headData.get("timePeriod");
//				String wsType = headData.get("type");
//				String route;
//				surveyName = "走行时间调查_"+ station +"站_"+timePeriod +"调查_"+ wsType +"_" + name +"_"  +date;
//				newFilePath = Environment.getExternalStorageDirectory()+"/客流调查/"+surveyName+".xlsx";
//				int rowStart;
//				if(timePeriod.equals("早高峰"))
//					rowStart = 4;
//				else if(timePeriod.equals("平峰"))
//					rowStart = 19;
//				else
//					rowStart = 34;
//
//				if(wsType.equals("进站-出站")){
//					String wslDireEToS = headData.get("wslDireEToS");
//					String wslDireSToE = headData.get("wslDireSToE");
//					String wslStartGLoc = headData.get("wslStartGLoc");
//					String wslEndGLoc = headData.get("wslEndGLoc");
//					String wslGLine = headData.get("wslGLine");
//					String wslTrainLine = headData.get("wslTrainLine");
//					if(wslGLine.equals(wslTrainLine))
//						route = "L"+wslGLine+"-L"+wslGLine+"-L"+wslGLine+"  同线";
//					else
//						route = "L"+wslGLine+"-L"+wslTrainLine+"-L"+wslGLine+"  异线";
//
//					assetData = assetManager.open("ExcelTemplate/WalkSurveyInoutTemplate.xlsx");
//					srcTemplateFile = new File(newFilePath);
//					FileUtils.ConcertInputStreamToFile(assetData,srcTemplateFile);
////					FileUtils.copyFile(Environment.getExternalStorageDirectory()+"/客流调查/进出站走行时间调查模板.xlsx",newFilePath);
//					file = new File(newFilePath);
//					fis = new FileInputStream(file);
//					workbook = new XSSFWorkbook(fis);
//					sheet = workbook.getSheetAt(0);
//					//表头
//					headCell = sheet.getRow(1).getCell(1);
//					headCell.setCellValue("调查路径："+route+"  调查单位：北航    车站名称："+station+"    调查员："+name+"    调查日期："+date);
//					sheet.getRow(2).getCell(9).setCellValue(wslStartGLoc);
//					sheet.getRow(2).getCell(10).setCellValue("L" + wslGLine + wslDireSToE);
//					sheet.getRow(2).getCell(19).setCellValue("L" + wslGLine + wslDireSToE);
//					sheet.getRow(2).getCell(20).setCellValue(wslEndGLoc);
//					sheet.getRow(2).getCell(28).setCellValue(wslEndGLoc);
//					sheet.getRow(2).getCell(29).setCellValue("L" + wslTrainLine + wslDireEToS);
//					sheet.getRow(2).getCell(38).setCellValue("L" + wslTrainLine + wslDireEToS);
//					sheet.getRow(2).getCell(39).setCellValue(wslStartGLoc);
//					//表体
//					for(HashMap<String,String> bodyData : totalDataArr){
//						switch (Integer.parseInt(bodyData.get("routeNo"))){
//							case 1:
//								int line1Count = Integer.parseInt(bodyData.get("line1Count"));
//								sheet.getRow(rowStart+line1Count).getCell(3).setCellValue(bodyData.get("speed"));
//								sheet.getRow(rowStart+line1Count).getCell(4).setCellValue(bodyData.get("age"));
//								sheet.getRow(rowStart+line1Count).getCell(5).setCellValue(bodyData.get("bagage"));
//								sheet.getRow(rowStart+line1Count).getCell(6).setCellValue(bodyData.get("walkTool"));
//								sheet.getRow(rowStart+line1Count).getCell(7).setCellValue(bodyData.get("telConcern"));
//								sheet.getRow(rowStart+line1Count).getCell(8).setCellValue(bodyData.get("sex"));
//								sheet.getRow(rowStart+line1Count).getCell(9).setCellValue(bodyData.get("gotoPlatformTime"));
//								sheet.getRow(rowStart+line1Count).getCell(10).setCellValue(bodyData.get("arrivePlatformTime"));
//								sheet.getRow(rowStart+line1Count).getCell(11).setCellValue(bodyData.get("openDoorTime2"));
//								break;
//							case 2:
//								int line2Count = Integer.parseInt(bodyData.get("line2Count"));
//								sheet.getRow(rowStart+line2Count).getCell(13).setCellValue(bodyData.get("speed"));
//								sheet.getRow(rowStart+line2Count).getCell(14).setCellValue(bodyData.get("age"));
//								sheet.getRow(rowStart+line2Count).getCell(15).setCellValue(bodyData.get("bagage"));
//								sheet.getRow(rowStart+line2Count).getCell(16).setCellValue(bodyData.get("walkTool"));
//								sheet.getRow(rowStart+line2Count).getCell(17).setCellValue(bodyData.get("telConcern"));
//								sheet.getRow(rowStart+line2Count).getCell(18).setCellValue(bodyData.get("sex"));
//								sheet.getRow(rowStart+line2Count).getCell(19).setCellValue(bodyData.get("openDoorTime1"));
//								sheet.getRow(rowStart+line2Count).getCell(20).setCellValue(bodyData.get("gogoutPlatformTime"));
//								break;
//							case 3:
//								int line3Count = Integer.parseInt(bodyData.get("line3Count"));
//								sheet.getRow(rowStart+line3Count).getCell(22).setCellValue(bodyData.get("speed"));
//								sheet.getRow(rowStart+line3Count).getCell(23).setCellValue(bodyData.get("age"));
//								sheet.getRow(rowStart+line3Count).getCell(24).setCellValue(bodyData.get("bagage"));
//								sheet.getRow(rowStart+line3Count).getCell(25).setCellValue(bodyData.get("walkTool"));
//								sheet.getRow(rowStart+line3Count).getCell(26).setCellValue(bodyData.get("telConcern"));
//								sheet.getRow(rowStart+line3Count).getCell(27).setCellValue(bodyData.get("sex"));
//								sheet.getRow(rowStart+line3Count).getCell(28).setCellValue(bodyData.get("gotoPlatformTime"));
//								sheet.getRow(rowStart+line3Count).getCell(29).setCellValue(bodyData.get("arrivePlatformTime"));
//								sheet.getRow(rowStart+line3Count).getCell(30).setCellValue(bodyData.get("openDoorTime2"));
//								break;
//							case 4:
//								int line4Count = Integer.parseInt(bodyData.get("line4Count"));
//								sheet.getRow(rowStart+line4Count).getCell(32).setCellValue(bodyData.get("speed"));
//								sheet.getRow(rowStart+line4Count).getCell(33).setCellValue(bodyData.get("age"));
//								sheet.getRow(rowStart+line4Count).getCell(34).setCellValue(bodyData.get("bagage"));
//								sheet.getRow(rowStart+line4Count).getCell(35).setCellValue(bodyData.get("walkTool"));
//								sheet.getRow(rowStart+line4Count).getCell(36).setCellValue(bodyData.get("telConcern"));
//								sheet.getRow(rowStart+line4Count).getCell(37).setCellValue(bodyData.get("sex"));
//								sheet.getRow(rowStart+line4Count).getCell(38).setCellValue(bodyData.get("openDoorTime1"));
//								sheet.getRow(rowStart+line4Count).getCell(39).setCellValue(bodyData.get("gogoutPlatformTime"));
//								break;
//						}
//					}
//				}
//		else if(wsType.equals("换乘")){
//					String wslEndLine = headData.get("wslEndLine");
//					String wslStartLine = headData.get("wslStartLine");
//					String wslStartLineStartDire = headData.get("wslStartLineStartDire");
//					String wslStartLineEndDire = headData.get("wslStartLineEndDire");
//					String wslEndLineStartDire = headData.get("wslEndLineStartDire");
//					String wslEndLineEndDire = headData.get("wslEndLineEndDire");
//
//					route = "L"+wslStartLine+"-L"+wslEndLine+"-L"+wslStartLine+"-L"+wslEndLine;
//
//					assetData = assetManager.open("ExcelTemplate/WalkSurveyTranTemplate.xlsx");
//					srcTemplateFile = new File(newFilePath);
//					FileUtils.ConcertInputStreamToFile(assetData,srcTemplateFile);
////					FileUtils.copyFile(Environment.getExternalStorageDirectory()+"/客流调查/换乘走行时间调查模板.xlsx",newFilePath);
//					file = new File(newFilePath);
//					fis = new FileInputStream(file);
//					workbook = new XSSFWorkbook(fis);
//					sheet = workbook.getSheetAt(0);
//					//表头
//					headCell = sheet.getRow(1).getCell(1);
//					headCell.setCellValue("调查路径："+route+"  调查单位：北航    车站名称："+station+"    调查员："+name+"    调查日期："+date);
//					sheet.getRow(2).getCell(9).setCellValue("L" + wslStartLine + wslStartLineStartDire);
//					sheet.getRow(2).getCell(10).setCellValue("L" + wslEndLine + wslEndLineStartDire);
//					sheet.getRow(2).getCell(19).setCellValue("L" + wslEndLine + wslEndLineStartDire);
//					sheet.getRow(2).getCell(20).setCellValue("L" + wslStartLine + wslStartLineEndDire);
//					sheet.getRow(2).getCell(29).setCellValue("L" + wslStartLine + wslStartLineEndDire);
//					sheet.getRow(2).getCell(30).setCellValue("L" + wslEndLine + wslEndLineEndDire);
//					sheet.getRow(2).getCell(39).setCellValue("L" + wslEndLine + wslEndLineEndDire);
//					sheet.getRow(2).getCell(40).setCellValue("L" + wslStartLine + wslStartLineStartDire);
//
//					//表体
//					for(HashMap<String,String> bodyData : totalDataArr){
//						switch (Integer.parseInt(bodyData.get("routeNo"))){
//							case 1:
//								int line1Count = Integer.parseInt(bodyData.get("line1Count"));
//								sheet.getRow(rowStart+line1Count).getCell(3).setCellValue(bodyData.get("speed"));
//								sheet.getRow(rowStart+line1Count).getCell(4).setCellValue(bodyData.get("age"));
//								sheet.getRow(rowStart+line1Count).getCell(5).setCellValue(bodyData.get("bagage"));
//								sheet.getRow(rowStart+line1Count).getCell(6).setCellValue(bodyData.get("walkTool"));
//								sheet.getRow(rowStart+line1Count).getCell(7).setCellValue(bodyData.get("telConcern"));
//								sheet.getRow(rowStart+line1Count).getCell(8).setCellValue(bodyData.get("sex"));
//								sheet.getRow(rowStart+line1Count).getCell(9).setCellValue(bodyData.get("openDoorTime1"));
//								sheet.getRow(rowStart+line1Count).getCell(10).setCellValue(bodyData.get("arrivePlatformTime"));
//								sheet.getRow(rowStart+line1Count).getCell(11).setCellValue(bodyData.get("openDoorTime2"));
//								break;
//							case 2:
//								int line2Count = Integer.parseInt(bodyData.get("line2Count"));
//								sheet.getRow(rowStart+line2Count).getCell(13).setCellValue(bodyData.get("speed"));
//								sheet.getRow(rowStart+line2Count).getCell(14).setCellValue(bodyData.get("age"));
//								sheet.getRow(rowStart+line2Count).getCell(15).setCellValue(bodyData.get("bagage"));
//								sheet.getRow(rowStart+line2Count).getCell(16).setCellValue(bodyData.get("walkTool"));
//								sheet.getRow(rowStart+line2Count).getCell(17).setCellValue(bodyData.get("telConcern"));
//								sheet.getRow(rowStart+line2Count).getCell(18).setCellValue(bodyData.get("sex"));
//								sheet.getRow(rowStart+line2Count).getCell(19).setCellValue(bodyData.get("openDoorTime1"));
//								sheet.getRow(rowStart+line2Count).getCell(20).setCellValue(bodyData.get("arrivePlatformTime"));
//								sheet.getRow(rowStart+line2Count).getCell(21).setCellValue(bodyData.get("openDoorTime2"));
//								break;
//							case 3:
//								int line3Count = Integer.parseInt(bodyData.get("line3Count"));
//								sheet.getRow(rowStart+line3Count).getCell(23).setCellValue(bodyData.get("speed"));
//								sheet.getRow(rowStart+line3Count).getCell(24).setCellValue(bodyData.get("age"));
//								sheet.getRow(rowStart+line3Count).getCell(25).setCellValue(bodyData.get("bagage"));
//								sheet.getRow(rowStart+line3Count).getCell(26).setCellValue(bodyData.get("walkTool"));
//								sheet.getRow(rowStart+line3Count).getCell(27).setCellValue(bodyData.get("telConcern"));
//								sheet.getRow(rowStart+line3Count).getCell(28).setCellValue(bodyData.get("sex"));
//								sheet.getRow(rowStart+line3Count).getCell(29).setCellValue(bodyData.get("openDoorTime1"));
//								sheet.getRow(rowStart+line3Count).getCell(30).setCellValue(bodyData.get("arrivePlatformTime"));
//								sheet.getRow(rowStart+line3Count).getCell(31).setCellValue(bodyData.get("openDoorTime2"));
//								break;
//							case 4:
//								int line4Count = Integer.parseInt(bodyData.get("line4Count"));
//								sheet.getRow(rowStart+line4Count).getCell(33).setCellValue(bodyData.get("speed"));
//								sheet.getRow(rowStart+line4Count).getCell(34).setCellValue(bodyData.get("age"));
//								sheet.getRow(rowStart+line4Count).getCell(35).setCellValue(bodyData.get("bagage"));
//								sheet.getRow(rowStart+line4Count).getCell(36).setCellValue(bodyData.get("walkTool"));
//								sheet.getRow(rowStart+line4Count).getCell(37).setCellValue(bodyData.get("telConcern"));
//								sheet.getRow(rowStart+line4Count).getCell(38).setCellValue(bodyData.get("sex"));
//								sheet.getRow(rowStart+line4Count).getCell(39).setCellValue(bodyData.get("openDoorTime1"));
//								sheet.getRow(rowStart+line4Count).getCell(40).setCellValue(bodyData.get("arrivePlatformTime"));
//								sheet.getRow(rowStart+line4Count).getCell(41).setCellValue(bodyData.get("openDoorTime2"));
//								break;
//						}
//					}
//				}
//
//				fis.close();
//				fos = new FileOutputStream(file);
//				workbook.write(fos);
//				fos.close();
//				break;
//			case AppConfig.STAY_SURVEY:
//				name = headData.get("name");
//				date = headData.get("date");
//				station = headData.get("station");
//				timePeriod = headData.get("timePeriod");
//				carriageLoc = headData.get("carriageLoc");
//				String gotoPlatformLoc = headData.get("gotoPlatformLoc");
//				String transferLoc = headData.get("transferLoc");
//				String platformLoc = headData.get("platformLoc");
//				surveyName = timePeriod + "留乘调查_" + station +"站_"+ carriageLoc +"_"+ name +"_"  +date;
//
//				newFilePath = Environment.getExternalStorageDirectory()+"/客流调查/"+surveyName+".xlsx";
//				assetData = assetManager.open("ExcelTemplate/StaySurveyTemplate.xlsx");
//				srcTemplateFile = new File(newFilePath);
//				FileUtils.ConcertInputStreamToFile(assetData,srcTemplateFile);
////				FileUtils.copyFile(Environment.getExternalStorageDirectory()+"/客流调查/留乘调查模板.xlsx",newFilePath);
//				file = new File(newFilePath);
//				fis = new FileInputStream(file);
//				workbook = new XSSFWorkbook(fis);
//				sheet = workbook.getSheetAt(0);
//				headCell = sheet.getRow(1).getCell(0);
//				headCell.setCellValue("调查日期："+date+"    调查时段："+timePeriod+"    调查员："+name+"    调查车站："+station+"    车厢位置："+carriageLoc+"\n"
//				+"进站闸机位置："+gotoPlatformLoc+"    换乘下车位置："+transferLoc+"    调查站台位置："+platformLoc);
//
//				for(int i=0;i<totalDataArr.size();i++) {
//					HashMap<String, String> hashMap = totalDataArr.get(i);
//					String ssType = hashMap.get("ssType");
//					if(hashMap.get("sex").equals("男"))
//						sheet.getRow(6+i).getCell(1).setCellValue("√");
//					else
//						sheet.getRow(6+i).getCell(2).setCellValue("√");
//					if(hashMap.get("age").equals("老年"))
//						sheet.getRow(6+i).getCell(3).setCellValue("√");
//					else if(hashMap.get("age").equals("中年"))
//						sheet.getRow(6+i).getCell(4).setCellValue("√");
//					else if(hashMap.get("age").equals("青年"))
//						sheet.getRow(6+i).getCell(5).setCellValue("√");
//					if(hashMap.get("bagageType").equals("大件行李"))
//						sheet.getRow(6+i).getCell(6).setCellValue("√");
//					else if(hashMap.get("bagageType").equals("一般行李"))
//						sheet.getRow(6+i).getCell(7).setCellValue("√");
//					else if(hashMap.get("bagageType").equals("无行李"))
//						sheet.getRow(6+i).getCell(8).setCellValue("√");
//					if(ssType.equals("进站")){
//						sheet.getRow(3).getCell(10).setCellValue("√");
//						sheet.getRow(6+i).getCell(9).setCellValue(hashMap.get("gotoPlatformTime"));
//					}else{
//						sheet.getRow(4).getCell(10).setCellValue("√");
//						sheet.getRow(6+i).getCell(9).setCellValue(hashMap.get("getoffTransferTime"));
//					}
//					sheet.getRow(6+i).getCell(11).setCellValue(hashMap.get("arrivePlatformTime"));
//					sheet.getRow(6+i).getCell(12).setCellValue(hashMap.get("orignLineNum"));
//					sheet.getRow(6+i).getCell(13).setCellValue(hashMap.get("getoffNum1"));
//					sheet.getRow(6+i).getCell(14).setCellValue(hashMap.get("getonNum1"));
//					sheet.getRow(6+i).getCell(15).setCellValue(hashMap.get("trainStart1"));
//					if(hashMap.get("trainStart2")!=null){
//						sheet.getRow(6+i).getCell(16).setCellValue(hashMap.get("getoffNum2"));
//						sheet.getRow(6+i).getCell(17).setCellValue(hashMap.get("getonNum2"));
//						sheet.getRow(6+i).getCell(18).setCellValue(hashMap.get("trainStart2"));
//						if(hashMap.get("trainStart3")!=null){
//							sheet.getRow(6+i).getCell(19).setCellValue(hashMap.get("getoffNum3"));
//							sheet.getRow(6+i).getCell(20).setCellValue(hashMap.get("getonNum3"));
//							sheet.getRow(6+i).getCell(21).setCellValue(hashMap.get("trainStart3"));
//							if(hashMap.get("trainStart4")!=null){
//								sheet.getRow(6+i).getCell(22).setCellValue(hashMap.get("getoffNum4"));
//								sheet.getRow(6+i).getCell(23).setCellValue(hashMap.get("getonNum4"));
//								sheet.getRow(6+i).getCell(24).setCellValue(hashMap.get("trainStart4"));
//								if(hashMap.get("trainStart5")!=null){
//									sheet.getRow(6+i).getCell(25).setCellValue(hashMap.get("getoffNum5"));
//									sheet.getRow(6+i).getCell(26).setCellValue(hashMap.get("getonNum5"));
//									sheet.getRow(6+i).getCell(27).setCellValue(hashMap.get("trainStart5"));
//									if(hashMap.get("trainStart6")!=null){
//										sheet.getRow(6+i).getCell(28).setCellValue(hashMap.get("getoffNum6"));
//										sheet.getRow(6+i).getCell(29).setCellValue(hashMap.get("getonNum6"));
//										sheet.getRow(6+i).getCell(30).setCellValue(hashMap.get("trainStart6"));
//									}
//								}
//							}
//						}
//					}
//				}
//				fis.close();// 关闭文件输入流
//				fos = new FileOutputStream(file);
//				workbook.write(fos);
//				fos.close();
//				break;
			case AppConstant.TRANSFER_SURVEY:
				name = headData.get("name");
				date = headData.get("date");
				station = headData.get("station");
				dire = headData.get("dire");
				timePeriod = headData.get("timePeriod");
				surveyName = timePeriod + "换乘量调查_" + station +"站_"+ dire +"_" + name +"_"  +date;


				newFilePath = Environment.getExternalStorageDirectory()+"/客流调查/"+surveyName+".xlsx";
				assetData = assetManager.open("ExcelTemplate/TransferSurveyTemplate.xlsx");
				srcTemplateFile = new File(newFilePath);
				FileUtils.ConcertInputStreamToFile(assetData,srcTemplateFile);
//				FileUtils.copyFile(Environment.getExternalStorageDirectory()+"/客流调查/TransferSurveyTemplate.xlsx",newFilePath);
				file = new File(newFilePath);
				fis = new FileInputStream(file);

				workbook = new XSSFWorkbook(fis);
				sheet = workbook.getSheetAt(0);
				headCell = sheet.getRow(1).getCell(0);
				headCell.setCellValue("车站："+station+"  调查点位："+dire+"  调查日期："+date+"  调查人："+name);

				for(int i=0;i<totalDataArr.size();i++){
					HashMap<String,String> hashMap = totalDataArr.get(i);
					sheet.getRow(i+3).getCell(0).setCellValue(hashMap.get("surveyTime"));
					sheet.getRow(i+3).getCell(1).setCellValue(hashMap.get("count"));
				}
				fis.close();// 关闭文件输入流
				fos = new FileOutputStream(file);
				workbook.write(fos);
				fos.close();
				break;
//			case AppConfig.OD_SURVEY:
//				for(int i=0;i<totalDataArr.size();i++) {
//					HashMap<String, String> hashMap = totalDataArr.get(i);
//				}
//				//汇总表Excel导出
//				name = headData.get("name");
//				date = headData.get("date");
//				dire = headData.get("dire");
//				String srcStation = headData.get("srcStation");
//				String desStation = headData.get("desStation");
//				String transferCount ="2";
//				surveyName = "OD调查_" + srcStation +"_"+ desStation +"_" + name +"_"  +date+"(总)";
//
//				newFilePath = Environment.getExternalStorageDirectory()+"/客流调查/"+surveyName+".xlsx";
//				assetData = assetManager.open("ExcelTemplate/ODSurveyTotalTemplate.xlsx");
//				srcTemplateFile = new File(newFilePath);
//				FileUtils.ConcertInputStreamToFile(assetData,srcTemplateFile);
////				FileUtils.copyFile(Environment.getExternalStorageDirectory()+"/客流调查/OD调查汇总模板.xlsx",newFilePath);
//				file = new File(newFilePath);
//				fis = new FileInputStream(file);
//				workbook = new XSSFWorkbook(fis);
//				sheet = workbook.getSheetAt(0);
//				for(int i=0;i<totalDataArr.size();i++){
//					HashMap<String,String> hashMap = totalDataArr.get(i);
//					transferCount = hashMap.get("transferCount");
//					sheet.getRow(i+1).getCell(0).setCellValue(hashMap.get("date"));
//					sheet.getRow(i+1).getCell(1).setCellValue(hashMap.get("IDNo"));
//					sheet.getRow(i+1).getCell(2).setCellValue(hashMap.get("name"));
//					sheet.getRow(i+1).getCell(3).setCellValue(hashMap.get("cardNum"));
//					sheet.getRow(i+1).getCell(4).setCellValue(hashMap.get("station"));
//					sheet.getRow(i+1).getCell(5).setCellValue(hashMap.get("line"));
//					sheet.getRow(i+1).getCell(6).setCellValue(hashMap.get("getinStationTime"));
//					sheet.getRow(i+1).getCell(7).setCellValue(hashMap.get("arriveStationTime1"));
//					sheet.getRow(i+1).getCell(8).setCellValue(hashMap.get("trainDire1"));
//					sheet.getRow(i+1).getCell(9).setCellValue(hashMap.get("shift1"));
//					sheet.getRow(i+1).getCell(10).setCellValue(hashMap.get("trainStartTime1"));
//					sheet.getRow(i+1).getCell(11).setCellValue(hashMap.get("getOffStation1"));
//					sheet.getRow(i+1).getCell(12).setCellValue(hashMap.get("getoffStationTime1"));
//					sheet.getRow(i+1).getCell(28).setCellValue(hashMap.get("stationCount"));
//					sheet.getRow(i+1).getCell(29).setCellValue(hashMap.get("distance"));
//					sheet.getRow(i+1).getCell(30).setCellValue(hashMap.get("transferCount"));
//					sheet.getRow(i+1).getCell(31).setCellValue(SurveyUtils.getTimeInterval(hashMap.get("getoutStationTime"),hashMap.get("getinStationTime")));
//
//
//					if(transferCount.equals("0")){
//						//无换乘
//						sheet.getRow(i+1).getCell(13).setCellValue(hashMap.get("getoutStationTime"));
//					}else{
//						sheet.getRow(i+1).getCell(13).setCellValue(hashMap.get("transferLine1"));
//						sheet.getRow(i+1).getCell(14).setCellValue(hashMap.get("arriveStationTime2"));
//						sheet.getRow(i+1).getCell(15).setCellValue(hashMap.get("trainDire2"));
//						sheet.getRow(i+1).getCell(16).setCellValue(hashMap.get("shift2"));
//						sheet.getRow(i+1).getCell(17).setCellValue(hashMap.get("trainStartTime2"));
//						sheet.getRow(i+1).getCell(18).setCellValue(hashMap.get("getOffStation2"));
//						sheet.getRow(i+1).getCell(19).setCellValue(hashMap.get("getoffStationTime2"));
//						if(transferCount.equals("1")){
//							//换乘一次
//							sheet.getRow(i+1).getCell(20).setCellValue(hashMap.get("getoutStationTime"));
//						}else{
//							//换乘两次
//							sheet.getRow(i+1).getCell(20).setCellValue(hashMap.get("transferLine2"));
//							sheet.getRow(i+1).getCell(21).setCellValue(hashMap.get("arriveStationTime3"));
//							sheet.getRow(i+1).getCell(22).setCellValue(hashMap.get("trainDire3"));
//							sheet.getRow(i+1).getCell(23).setCellValue(hashMap.get("shift3"));
//							sheet.getRow(i+1).getCell(24).setCellValue(hashMap.get("trainStartTime3"));
//							sheet.getRow(i+1).getCell(25).setCellValue(hashMap.get("getOffStation3"));
//							sheet.getRow(i+1).getCell(26).setCellValue(hashMap.get("getoffStationTime3"));
//							sheet.getRow(i+1).getCell(27).setCellValue(hashMap.get("getoutStationTime"));
//						}
//					}
//				}
//				fis.close();
//				fos = new FileOutputStream(file);
//				workbook.write(fos);
//				fos.close();
//
//
//				//调查明细表Excel导出
//				surveyName = "OD调查_" + srcStation +"_"+ desStation +"_" + name +"_"  +date+"(分)";
//				newFilePath = Environment.getExternalStorageDirectory()+"/客流调查/"+surveyName+".xlsx";
//				assetData = assetManager.open("ExcelTemplate/ODSurveyDetailTemplate.xlsx");
//				srcTemplateFile = new File(newFilePath);
//				FileUtils.ConcertInputStreamToFile(assetData,srcTemplateFile);
////				FileUtils.copyFile(Environment.getExternalStorageDirectory()+"/客流调查/OD调查记录模板.xlsx",newFilePath);
//				file = new File(newFilePath);
//				fis = new FileInputStream(file);
//				workbook = new XSSFWorkbook(fis);
//				//TODO 没有做多条数据根据transferCount分sheet录入功能
//				String transCount = headData.get("transferCount");
//				sheet = workbook.getSheetAt(Integer.parseInt(transferCount));
//				//设置表头
//				sheet.getRow(1).getCell(0).setCellValue("身份证号："+headData.get("IDNo"));
//				sheet.getRow(1).getCell(3).setCellValue("姓名："+headData.get("name"));
//				sheet.getRow(1).getCell(5).setCellValue("卡号："+headData.get("cardNum"));
//
//				for(int i=0;i<totalDataArr.size();i++) {
//					HashMap<String, String> hashMap = totalDataArr.get(i);
//					sheet.getRow(2).getCell(i+1).setCellValue(hashMap.get("date"));
//					sheet.getRow(3).getCell(i+1).setCellValue(hashMap.get("station"));
//					sheet.getRow(4).getCell(i+1).setCellValue(hashMap.get("line"));
//					sheet.getRow(5).getCell(i+1).setCellValue(hashMap.get("getinStationTime"));
//					sheet.getRow(6).getCell(i+1).setCellValue(hashMap.get("arriveStationTime1"));
//					sheet.getRow(7).getCell(i+1).setCellValue(hashMap.get("trainDire1"));
//					sheet.getRow(8).getCell(i+1).setCellValue(hashMap.get("shift1"));
//					sheet.getRow(9).getCell(i+1).setCellValue(hashMap.get("trainStartTime1"));
//					sheet.getRow(10).getCell(i+1).setCellValue(hashMap.get("getOffStation1"));
//					sheet.getRow(11).getCell(i+1).setCellValue(hashMap.get("getoffStationTime1"));
//					if(transCount.equals("0")){
//						sheet.getRow(12).getCell(i+1).setCellValue(hashMap.get("getoutStationTime"));
//						sheet.getRow(13).getCell(i+1).setCellValue(hashMap.get("stationCount"));
//						sheet.getRow(14).getCell(i+1).setCellValue(hashMap.get("distance"));
//						sheet.getRow(15).getCell(i+1).setCellValue(hashMap.get("transferCount"));
//						sheet.getRow(16).getCell(i+1).setCellValue(SurveyUtils.getTimeInterval(hashMap.get("getoutStationTime"),hashMap.get("getinStationTime")));
//					}else{
//						sheet.getRow(12).getCell(i+1).setCellValue(hashMap.get("transferLine1"));
//						sheet.getRow(13).getCell(i+1).setCellValue(hashMap.get("arriveStationTime2"));
//						sheet.getRow(14).getCell(i+1).setCellValue(hashMap.get("trainDire2"));
//						sheet.getRow(15).getCell(i+1).setCellValue(hashMap.get("shift2"));
//						sheet.getRow(16).getCell(i+1).setCellValue(hashMap.get("trainStartTime2"));
//						sheet.getRow(17).getCell(i+1).setCellValue(hashMap.get("getOffStation2"));
//						sheet.getRow(18).getCell(i+1).setCellValue(hashMap.get("getoffStationTime2"));
//						if(transCount.equals("1")){
//							sheet.getRow(19).getCell(i+1).setCellValue(hashMap.get("getoutStationTime"));
//							sheet.getRow(20).getCell(i+1).setCellValue(hashMap.get("stationCount"));
//							sheet.getRow(21).getCell(i+1).setCellValue(hashMap.get("distance"));
//							sheet.getRow(22).getCell(i+1).setCellValue(hashMap.get("transferCount"));
//							sheet.getRow(23).getCell(i+1).setCellValue(SurveyUtils.getTimeInterval(hashMap.get("getoutStationTime"),hashMap.get("getinStationTime")));
//						}else{
//							sheet.getRow(19).getCell(i+1).setCellValue(hashMap.get("transferLine2"));
//							sheet.getRow(20).getCell(i+1).setCellValue(hashMap.get("arriveStationTime3"));
//							sheet.getRow(21).getCell(i+1).setCellValue(hashMap.get("trainDire3"));
//							sheet.getRow(22).getCell(i+1).setCellValue(hashMap.get("shift3"));
//							sheet.getRow(23).getCell(i+1).setCellValue(hashMap.get("trainStartTime3"));
//							sheet.getRow(24).getCell(i+1).setCellValue(hashMap.get("getOffStation3"));
//							sheet.getRow(25).getCell(i+1).setCellValue(hashMap.get("getoffStationTime3"));
//							sheet.getRow(26).getCell(i+1).setCellValue(hashMap.get("getoutStationTime"));
//
//							sheet.getRow(27).getCell(i+1).setCellValue(hashMap.get("stationCount"));
//							sheet.getRow(28).getCell(i+1).setCellValue(hashMap.get("distance"));
//							sheet.getRow(29).getCell(i+1).setCellValue(hashMap.get("transferCount"));
//							sheet.getRow(30).getCell(i+1).setCellValue(SurveyUtils.getTimeInterval(hashMap.get("getoutStationTime"),hashMap.get("getinStationTime")));
//						}
//					}
//					fis.close();
//					fos = new FileOutputStream(file);
//					workbook.write(fos);
//					fos.close();
//				}
//				break;
//			case AppConfig.REVERSE_SURVEY:
//				name = headData.get("name");
//				date = headData.get("date");
//				station = headData.get("station");
//				dire = headData.get("dire");
//				type = headData.get("type");
//				carriageLoc = headData.get("carriageLoc");
//				timePeriod = headData.get("timePeriod");
//
//				//反向乘车调查_沙河站_平峰上车_刘笑凡
//				if(timePeriod.equals("07:00~09:00"))
//					surveyName = "反向乘车调查_" + station +"站_高峰"+type+"_" + name +"_"  + date;
//				else if(timePeriod.equals("10:00~12:00"))
//					surveyName = "反向乘车调查_" + station +"站_平峰+"+type+"+_" + name +"_"  + date;
//
//				newFilePath = Environment.getExternalStorageDirectory()+"/客流调查/"+surveyName+".xlsx";
//
//				assetData = assetManager.open("ExcelTemplate/ReverseSurveyTemplate.xlsx");
//				srcTemplateFile = new File(newFilePath);
//				FileUtils.ConcertInputStreamToFile(assetData,srcTemplateFile);
////				FileUtils.copyFile(Environment.getExternalStorageDirectory()+"/客流调查/反向乘车调查模板.xlsx",newFilePath);
//				file = new File(newFilePath);
//				fis = new FileInputStream(file);
//
//				workbook = new XSSFWorkbook(fis);
//				sheet = workbook.getSheetAt(0);
//				headCell = sheet.getRow(1).getCell(0);
//				headCell.setCellValue("车站："+station+"  调查点位："+dire+"  调查日期："+date+"  调查人："+name);
//				headCell.setCellValue("调查日期："+date+"    调查时段："+timePeriod+"    调查员："+name+"\n调查车站："+station+"    调查位置："+dire);
//				sheet.getRow(2).getCell(1).setCellValue("（开往"+ dire +"方向）\n" +	"上车人数统计");
//
//				for(int i=0;i<totalDataArr.size();i++){
//					HashMap<String,String> hashMap = totalDataArr.get(i);
//					sheet.getRow(i+3).getCell(1).setCellValue(hashMap.get("count"));
//				}
//				fis.close();
//				fos = new FileOutputStream(file);
//				workbook.write(fos);
//				fos.close();
//				break;
		}
	}

	public static String getTimeInterval(String endDate, String startDate) {

		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		long ns = 1000;

		//11:23:21 11:25:04
		long endTime = Integer.parseInt(endDate.substring(0,2))*nh+ Integer.parseInt(endDate.substring(3,5))*nm+ ns* Integer.parseInt(endDate.substring(6,8));
		long startTime = Integer.parseInt(startDate.substring(0,2))*nh+ Integer.parseInt(startDate.substring(3,5))*nm+ ns* Integer.parseInt(startDate.substring(6,8));

		String v_hour,v_min,v_second;
		// 获得两个时间的毫秒时间差异
		long diff = endTime - startTime;
		// 计算差多少小时
		long hour = diff % nd / nh;
		if(hour<10)
			v_hour = "0"+hour;
		else
			v_hour = hour + "";
		// 计算差多少分钟
		long min = diff % nd % nh / nm;
		if(min<10)
			v_min = "0"+min;
		else
			v_min = min+"";
		// 计算差多少秒//输出结果
		 long sec = diff % nd % nh % nm / ns;
		if(sec<10)
			v_second = "0"+sec;
		else
			v_second = ""+sec;
		return v_hour+":"+v_min+":"+v_second;
	}

//	public static Boolean isDataNeedExport(String surveyName, Context context, UserEntity user){
//		Cursor cursor = null;
//		if(surveyName.equals("走行调查")){
//			cursor = WalkSurveyDataHelper.queryWalkSurveyData(context);
//			if(cursor.getCount()==0){
//				return false;
//			}
//			cursor.moveToFirst();
//			if(!cursor.getString(cursor.getColumnIndex(WalkSurveyEntity.TWalkType)).equals(user.getWsType()))
//				return true;
//			return false;
//		}
//		else if(surveyName.equals("OD调查")){
//			cursor = ODSurveyDataHelper.queryODSurveyData(context);
//			if(cursor.getCount()==0){
//				return false;
//			}
//			cursor.moveToFirst();
//			String transferCount = cursor.getString(cursor.getColumnIndex(ODSurveyEntity.TTransferCount));
//			if((transferCount.equals("0")&&!user.getOdType().equals("无换乘"))||(transferCount.equals("1")&&!user.getOdType().equals("换乘一次"))||(transferCount.equals("2")&&!user.getOdType().equals("换乘两次"))){
//				return true;
//			}
//		}
//
//		return false;
//	}


}