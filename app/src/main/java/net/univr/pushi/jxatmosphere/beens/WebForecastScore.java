package net.univr.pushi.jxatmosphere.beens;

import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2019/01/21
 * desc   :
 * version: 1.0
 */


public class WebForecastScore {


    /**
     * data : {"agings":[24,48,72,96,120,144,168,192,216,240],"forecas":[{"algorithmname":"ts","areaid":"jiangxi","examineid":1,"forecasid":"guidance","value":["0.7564","0.846","0.8594","0.7485","0.5796","0.6949","0.7762","0.5706","0.4391","0.6016"]},{"algorithmname":"sum","areaid":"jiangxi","examineid":1,"forecasid":"guidance","value":["706","708","704","672","678","672","706","687","706","728"]},{"algorithmname":"sum_n11","areaid":"jiangxi","examineid":1,"forecasid":"guidance","value":["534","599","605","503","393","467","548","392","310","438"]},{"algorithmname":"sum_n01","areaid":"jiangxi","examineid":1,"forecasid":"guidance","value":["113","48","40","78","145","91","23","170","298","31"]},{"algorithmname":"sum_n10","areaid":"jiangxi","examineid":1,"forecasid":"guidance","value":["59","61","59","91","140","114","135","125","98","259"]},{"algorithmname":"ts","areaid":"ffuzhou","examineid":1,"forecasid":"guidance","value":["0.8902","0.875","0.9506","0.9136","0.7077","0.8814","0.7568","0.4909","0.5362","0.5422"]},{"algorithmname":"sum","areaid":"ffuzhou","examineid":1,"forecasid":"guidance","value":["82","80","81","81","65","59","74","55","69","83"]},{"algorithmname":"sum_n11","areaid":"ffuzhou","examineid":1,"forecasid":"guidance","value":["73","70","77","74","46","52","56","27","37","45"]},{"algorithmname":"sum_n01","areaid":"ffuzhou","examineid":1,"forecasid":"guidance","value":["3","5","0","0","6","2","1","18","26","4"]},{"algorithmname":"sum_n10","areaid":"ffuzhou","examineid":1,"forecasid":"guidance","value":["6","5","4","7","13","5","17","10","6","34"]},{"algorithmname":"ts","areaid":"ganzhou","examineid":1,"forecasid":"guidance","value":["0.3354","0.7349","0.6975","0.6025","0.5123","0.4969","0.7914","0.5247","0.5786","0.4049"]},{"algorithmname":"sum","areaid":"ganzhou","examineid":1,"forecasid":"guidance","value":["164","166","162","161","162","163","163","162","159","163"]},{"algorithmname":"sum_n11","areaid":"ganzhou","examineid":1,"forecasid":"guidance","value":["55","122","113","97","83","81","129","85","92","66"]},{"algorithmname":"sum_n01","areaid":"ganzhou","examineid":1,"forecasid":"guidance","value":["104","32","40","46","10","16","17","6","19","5"]},{"algorithmname":"sum_n10","areaid":"ganzhou","examineid":1,"forecasid":"guidance","value":["5","12","9","18","69","66","17","71","48","92"]},{"algorithmname":"ts","areaid":"jian","examineid":1,"forecasid":"guidance","value":["0.7907","0.7471","0.8353","0.8519","0.4651","0.6429","0.4268","0.5294","0.375","0.573"]},{"algorithmname":"sum","areaid":"jian","examineid":1,"forecasid":"guidance","value":["86","87","85","81","86","84","82","85","88","89"]},{"algorithmname":"sum_n11","areaid":"jian","examineid":1,"forecasid":"guidance","value":["68","65","71","69","40","54","35","45","33","51"]},{"algorithmname":"sum_n01","areaid":"jian","examineid":1,"forecasid":"guidance","value":["5","11","0","0","9","6","5","22","37","1"]},{"algorithmname":"sum_n10","areaid":"jian","examineid":1,"forecasid":"guidance","value":["13","11","14","12","37","24","42","18","18","37"]},{"algorithmname":"ts","areaid":"jingdezhen","examineid":1,"forecasid":"guidance","value":["0.7778","0.7778","0.8333","0.6111","0.8333","0.6923","0.7778","0.5","0.5789","1"]},{"algorithmname":"sum","areaid":"jingdezhen","examineid":1,"forecasid":"guidance","value":["18","18","18","18","18","13","18","18","19","18"]},{"algorithmname":"sum_n11","areaid":"jingdezhen","examineid":1,"forecasid":"guidance","value":["14","14","15","11","15","9","14","9","11","18"]},{"algorithmname":"sum_n01","areaid":"jingdezhen","examineid":1,"forecasid":"guidance","value":["0","0","0","0","3","4","0","6","8","0"]},{"algorithmname":"sum_n10","areaid":"jingdezhen","examineid":1,"forecasid":"guidance","value":["4","4","3","7","0","0","4","3","0","0"]},{"algorithmname":"ts","areaid":"jiujiang","examineid":1,"forecasid":"guidance","value":["0.9324","0.9296","0.9296","0.8462","0.6957","0.7808","0.8974","0.7922","0.7792","0.7975"]},{"algorithmname":"sum","areaid":"jiujiang","examineid":1,"forecasid":"guidance","value":["74","71","71","65","69","73","78","77","77","79"]},{"algorithmname":"sum_n11","areaid":"jiujiang","examineid":1,"forecasid":"guidance","value":["69","66","66","55","48","57","70","61","60","63"]},{"algorithmname":"sum_n01","areaid":"jiujiang","examineid":1,"forecasid":"guidance","value":["1","0","0","7","20","9","0","8","17","5"]},{"algorithmname":"sum_n10","areaid":"jiujiang","examineid":1,"forecasid":"guidance","value":["4","5","5","3","1","7","8","8","0","11"]},{"algorithmname":"ts","areaid":"nanchang","examineid":1,"forecasid":"guidance","value":["1","0.9714","1","0.75","0.6944","0.8611","0.9429","0.7647","0.2647","0.6857"]},{"algorithmname":"sum","areaid":"nanchang","examineid":1,"forecasid":"guidance","value":["35","35","35","32","36","36","35","34","34","35"]},{"algorithmname":"sum_n11","areaid":"nanchang","examineid":1,"forecasid":"guidance","value":["35","34","35","24","25","31","33","26","9","24"]},{"algorithmname":"sum_n01","areaid":"nanchang","examineid":1,"forecasid":"guidance","value":["0","0","0","8","11","4","0","8","25","4"]},{"algorithmname":"sum_n10","areaid":"nanchang","examineid":1,"forecasid":"guidance","value":["0","1","0","0","0","1","2","0","0","7"]},{"algorithmname":"ts","areaid":"pingxiang","examineid":1,"forecasid":"guidance","value":["1","1","1","0.875","0.6667","0.6667","0.8182","0.5455","0.1739","0.6957"]},{"algorithmname":"sum","areaid":"pingxiang","examineid":1,"forecasid":"guidance","value":["16","18","20","16","18","18","22","22","23","23"]},{"algorithmname":"sum_n11","areaid":"pingxiang","examineid":1,"forecasid":"guidance","value":["16","18","20","14","12","12","18","12","4","16"]},{"algorithmname":"sum_n01","areaid":"pingxiang","examineid":1,"forecasid":"guidance","value":["0","0","0","0","4","6","0","10","11","0"]},{"algorithmname":"sum_n10","areaid":"pingxiang","examineid":1,"forecasid":"guidance","value":["0","0","0","2","2","0","4","0","8","7"]},{"algorithmname":"ts","areaid":"shangrao","examineid":1,"forecasid":"guidance","value":["0.88","0.9109","0.9118","0.8061","0.7188","0.7188","0.7961","0.5619","0.3056","0.7064"]},{"algorithmname":"sum","areaid":"shangrao","examineid":1,"forecasid":"guidance","value":["100","101","102","98","96","96","103","105","108","109"]},{"algorithmname":"sum_n11","areaid":"shangrao","examineid":1,"forecasid":"guidance","value":["88","92","93","79","69","69","82","59","33","77"]},{"algorithmname":"sum_n01","areaid":"shangrao","examineid":1,"forecasid":"guidance","value":["0","0","0","2","22","24","0","40","75","7"]},{"algorithmname":"sum_n10","areaid":"shangrao","examineid":1,"forecasid":"guidance","value":["12","9","9","17","5","3","21","6","0","25"]},{"algorithmname":"ts","areaid":"xinyu","examineid":1,"forecasid":"guidance","value":["0.8889","0.8889","0.8889","0.5882","0.3125","0.9412","0.8333","0.5","0.2222","0.6875"]},{"algorithmname":"sum","areaid":"xinyu","examineid":1,"forecasid":"guidance","value":["18","18","18","17","16","17","18","18","18","16"]},{"algorithmname":"sum_n11","areaid":"xinyu","examineid":1,"forecasid":"guidance","value":["16","16","16","10","5","16","15","9","4","11"]},{"algorithmname":"sum_n01","areaid":"xinyu","examineid":1,"forecasid":"guidance","value":["0","0","0","1","6","0","0","8","12","0"]},{"algorithmname":"sum_n10","areaid":"xinyu","examineid":1,"forecasid":"guidance","value":["2","2","2","6","5","1","3","1","2","5"]},{"algorithmname":"ts","areaid":"yichun","examineid":1,"forecasid":"guidance","value":["0.8776","0.8776","0.8854","0.6782","0.375","0.7732","0.866","0.5313","0.2604","0.5773"]},{"algorithmname":"sum","areaid":"yichun","examineid":1,"forecasid":"guidance","value":["98","98","96","87","96","97","97","96","96","97"]},{"algorithmname":"sum_n11","areaid":"yichun","examineid":1,"forecasid":"guidance","value":["86","86","85","59","36","75","84","51","25","56"]},{"algorithmname":"sum_n01","areaid":"yichun","examineid":1,"forecasid":"guidance","value":["0","0","0","14","53","16","0","38","56","3"]},{"algorithmname":"sum_n10","areaid":"yichun","examineid":1,"forecasid":"guidance","value":["12","12","11","14","7","6","13","7","15","38"]},{"algorithmname":"ts","areaid":"yingtan","examineid":1,"forecasid":"guidance","value":["0.9333","1","0.875","0.6875","0.875","0.6875","0.75","0.5333","0.1333","0.6875"]},{"algorithmname":"sum","areaid":"yingtan","examineid":1,"forecasid":"guidance","value":["15","16","16","16","16","16","16","15","15","16"]},{"algorithmname":"sum_n11","areaid":"yingtan","examineid":1,"forecasid":"guidance","value":["14","16","14","11","14","11","12","8","2","11"]},{"algorithmname":"sum_n01","areaid":"yingtan","examineid":1,"forecasid":"guidance","value":["0","0","0","0","1","4","0","6","12","2"]},{"algorithmname":"sum_n10","areaid":"yingtan","examineid":1,"forecasid":"guidance","value":["1","0","2","5","1","1","4","1","1","3"]},{"algorithmname":"ts","areaid":"jiangxi","examineid":1,"forecasid":"ybmode7","value":["0.7592","0.8333","0.8608","0.5952","0.5811","0.631","0.6671","0.5706","0.4391","0.6016"]},{"algorithmname":"sum","areaid":"jiangxi","examineid":1,"forecasid":"ybmode7","value":["706","708","704","672","678","672","706","687","706","728"]},{"algorithmname":"sum_n11","areaid":"jiangxi","examineid":1,"forecasid":"ybmode7","value":["536","590","606","400","394","424","471","392","310","438"]},{"algorithmname":"sum_n01","areaid":"jiangxi","examineid":1,"forecasid":"ybmode7","value":["113","59","36","201","179","156","135","170","298","31"]},{"algorithmname":"sum_n10","areaid":"jiangxi","examineid":1,"forecasid":"ybmode7","value":["57","59","62","71","105","92","100","125","98","259"]},{"algorithmname":"ts","areaid":"ffuzhou","examineid":1,"forecasid":"ybmode7","value":["0.8902","0.875","0.9506","0.6914","0.6769","0.7458","0.6351","0.4909","0.5362","0.5422"]},{"algorithmname":"sum","areaid":"ffuzhou","examineid":1,"forecasid":"ybmode7","value":["82","80","81","81","65","59","74","55","69","83"]},{"algorithmname":"sum_n11","areaid":"ffuzhou","examineid":1,"forecasid":"ybmode7","value":["73","70","77","56","44","44","47","27","37","45"]},{"algorithmname":"sum_n01","areaid":"ffuzhou","examineid":1,"forecasid":"ybmode7","value":["3","5","0","20","12","12","16","18","26","4"]},{"algorithmname":"sum_n10","areaid":"ffuzhou","examineid":1,"forecasid":"ybmode7","value":["6","5","4","5","9","3","11","10","6","34"]},{"algorithmname":"ts","areaid":"ganzhou","examineid":1,"forecasid":"ybmode7","value":["0.3476","0.6807","0.7037","0.4969","0.6111","0.589","0.8098","0.5247","0.5786","0.4049"]},{"algorithmname":"sum","areaid":"ganzhou","examineid":1,"forecasid":"ybmode7","value":["164","166","162","161","162","163","163","162","159","163"]},{"algorithmname":"sum_n11","areaid":"ganzhou","examineid":1,"forecasid":"ybmode7","value":["57","113","114","80","99","96","132","85","92","66"]},{"algorithmname":"sum_n01","areaid":"ganzhou","examineid":1,"forecasid":"ybmode7","value":["104","43","36","65","11","16","18","6","19","5"]},{"algorithmname":"sum_n10","areaid":"ganzhou","examineid":1,"forecasid":"ybmode7","value":["3","10","12","16","52","51","13","71","48","92"]},{"algorithmname":"ts","areaid":"jian","examineid":1,"forecasid":"ybmode7","value":["0.7907","0.7471","0.8353","0.5556","0.4767","0.5595","0.5366","0.5294","0.375","0.573"]},{"algorithmname":"sum","areaid":"jian","examineid":1,"forecasid":"ybmode7","value":["86","87","85","81","86","84","82","85","88","89"]},{"algorithmname":"sum_n11","areaid":"jian","examineid":1,"forecasid":"ybmode7","value":["68","65","71","45","41","47","44","45","33","51"]},{"algorithmname":"sum_n01","areaid":"jian","examineid":1,"forecasid":"ybmode7","value":["5","11","0","24","17","16","10","22","37","1"]},{"algorithmname":"sum_n10","areaid":"jian","examineid":1,"forecasid":"ybmode7","value":["13","11","14","12","28","21","28","18","18","37"]},{"algorithmname":"ts","areaid":"jingdezhen","examineid":1,"forecasid":"ybmode7","value":["0.7778","0.7778","0.8333","0.6111","0.8333","0.6154","0.5556","0.5","0.5789","1"]},{"algorithmname":"sum","areaid":"jingdezhen","examineid":1,"forecasid":"ybmode7","value":["18","18","18","18","18","13","18","18","19","18"]},{"algorithmname":"sum_n11","areaid":"jingdezhen","examineid":1,"forecasid":"ybmode7","value":["14","14","15","11","15","8","10","9","11","18"]},{"algorithmname":"sum_n01","areaid":"jingdezhen","examineid":1,"forecasid":"ybmode7","value":["0","0","0","3","3","5","5","6","8","0"]},{"algorithmname":"sum_n10","areaid":"jingdezhen","examineid":1,"forecasid":"ybmode7","value":["4","4","3","4","0","0","3","3","0","0"]},{"algorithmname":"ts","areaid":"jiujiang","examineid":1,"forecasid":"ybmode7","value":["0.9324","0.9296","0.9296","0.6923","0.6522","0.6849","0.6154","0.7922","0.7792","0.7975"]},{"algorithmname":"sum","areaid":"jiujiang","examineid":1,"forecasid":"ybmode7","value":["74","71","71","65","69","73","78","77","77","79"]},{"algorithmname":"sum_n11","areaid":"jiujiang","examineid":1,"forecasid":"ybmode7","value":["69","66","66","45","45","50","48","61","60","63"]},{"algorithmname":"sum_n01","areaid":"jiujiang","examineid":1,"forecasid":"ybmode7","value":["1","0","0","18","23","18","23","8","17","5"]},{"algorithmname":"sum_n10","areaid":"jiujiang","examineid":1,"forecasid":"ybmode7","value":["4","5","5","2","1","5","7","8","0","11"]},{"algorithmname":"ts","areaid":"nanchang","examineid":1,"forecasid":"ybmode7","value":["1","0.9714","1","0.5625","0.6667","0.7222","0.7143","0.7647","0.2647","0.6857"]},{"algorithmname":"sum","areaid":"nanchang","examineid":1,"forecasid":"ybmode7","value":["35","35","35","32","36","36","35","34","34","35"]},{"algorithmname":"sum_n11","areaid":"nanchang","examineid":1,"forecasid":"ybmode7","value":["35","34","35","18","24","26","25","26","9","24"]},{"algorithmname":"sum_n01","areaid":"nanchang","examineid":1,"forecasid":"ybmode7","value":["0","0","0","14","12","9","9","8","25","4"]},{"algorithmname":"sum_n10","areaid":"nanchang","examineid":1,"forecasid":"ybmode7","value":["0","1","0","0","0","1","1","0","0","7"]},{"algorithmname":"ts","areaid":"pingxiang","examineid":1,"forecasid":"ybmode7","value":["1","1","1","0.75","0.5556","0.4444","0.6364","0.5455","0.1739","0.6957"]},{"algorithmname":"sum","areaid":"pingxiang","examineid":1,"forecasid":"ybmode7","value":["16","18","20","16","18","18","22","22","23","23"]},{"algorithmname":"sum_n11","areaid":"pingxiang","examineid":1,"forecasid":"ybmode7","value":["16","18","20","12","10","8","14","12","4","16"]},{"algorithmname":"sum_n01","areaid":"pingxiang","examineid":1,"forecasid":"ybmode7","value":["0","0","0","4","7","10","6","10","11","0"]},{"algorithmname":"sum_n10","areaid":"pingxiang","examineid":1,"forecasid":"ybmode7","value":["0","0","0","0","1","0","2","0","8","7"]},{"algorithmname":"ts","areaid":"shangrao","examineid":1,"forecasid":"ybmode7","value":["0.88","0.9109","0.9118","0.6531","0.6667","0.6042","0.6408","0.5619","0.3056","0.7064"]},{"algorithmname":"sum","areaid":"shangrao","examineid":1,"forecasid":"ybmode7","value":["100","101","102","98","96","96","103","105","108","109"]},{"algorithmname":"sum_n11","areaid":"shangrao","examineid":1,"forecasid":"ybmode7","value":["88","92","93","64","64","58","66","59","33","77"]},{"algorithmname":"sum_n01","areaid":"shangrao","examineid":1,"forecasid":"ybmode7","value":["0","0","0","21","28","35","20","40","75","7"]},{"algorithmname":"sum_n10","areaid":"shangrao","examineid":1,"forecasid":"ybmode7","value":["12","9","9","13","4","3","17","6","0","25"]},{"algorithmname":"ts","areaid":"xinyu","examineid":1,"forecasid":"ybmode7","value":["0.8889","0.8889","0.8889","0.4706","0.4375","0.8235","0.6111","0.5","0.2222","0.6875"]},{"algorithmname":"sum","areaid":"xinyu","examineid":1,"forecasid":"ybmode7","value":["18","18","18","17","16","17","18","18","18","16"]},{"algorithmname":"sum_n11","areaid":"xinyu","examineid":1,"forecasid":"ybmode7","value":["16","16","16","8","7","14","11","9","4","11"]},{"algorithmname":"sum_n01","areaid":"xinyu","examineid":1,"forecasid":"ybmode7","value":["0","0","0","4","6","2","4","8","12","0"]},{"algorithmname":"sum_n10","areaid":"xinyu","examineid":1,"forecasid":"ybmode7","value":["2","2","2","5","3","1","3","1","2","5"]},{"algorithmname":"ts","areaid":"yichun","examineid":1,"forecasid":"ybmode7","value":["0.8776","0.8776","0.8854","0.5977","0.3542","0.6701","0.6598","0.5313","0.2604","0.5773"]},{"algorithmname":"sum","areaid":"yichun","examineid":1,"forecasid":"ybmode7","value":["98","98","96","87","96","97","97","96","96","97"]},{"algorithmname":"sum_n11","areaid":"yichun","examineid":1,"forecasid":"ybmode7","value":["86","86","85","52","34","65","64","51","25","56"]},{"algorithmname":"sum_n01","areaid":"yichun","examineid":1,"forecasid":"ybmode7","value":["0","0","0","24","56","26","20","38","56","3"]},{"algorithmname":"sum_n10","areaid":"yichun","examineid":1,"forecasid":"ybmode7","value":["12","12","11","11","6","6","13","7","15","38"]},{"algorithmname":"ts","areaid":"yingtan","examineid":1,"forecasid":"ybmode7","value":["0.9333","1","0.875","0.5625","0.6875","0.5","0.625","0.5333","0.1333","0.6875"]},{"algorithmname":"sum","areaid":"yingtan","examineid":1,"forecasid":"ybmode7","value":["15","16","16","16","16","16","16","15","15","16"]},{"algorithmname":"sum_n11","areaid":"yingtan","examineid":1,"forecasid":"ybmode7","value":["14","16","14","9","11","8","10","8","2","11"]},{"algorithmname":"sum_n01","areaid":"yingtan","examineid":1,"forecasid":"ybmode7","value":["0","0","0","4","4","7","4","6","12","2"]},{"algorithmname":"sum_n10","areaid":"yingtan","examineid":1,"forecasid":"ybmode7","value":["1","0","2","3","1","1","2","1","1","3"]},{"algorithmname":"ts","areaid":"jiangxi","examineid":1,"forecasid":"scmoc","value":["0.6799","0.8362","0.8523","0.7574","0.6416","0.5417","0.8102","0.5604","0.4122","0.8297"]},{"algorithmname":"sum","areaid":"jiangxi","examineid":1,"forecasid":"scmoc","value":["706","708","704","672","678","672","706","687","706","728"]},{"algorithmname":"sum_n11","areaid":"jiangxi","examineid":1,"forecasid":"scmoc","value":["480","592","600","509","435","364","572","385","291","604"]},{"algorithmname":"sum_n01","areaid":"jiangxi","examineid":1,"forecasid":"scmoc","value":["169","46","32","54","159","145","30","155","356","52"]},{"algorithmname":"sum_n10","areaid":"jiangxi","examineid":1,"forecasid":"scmoc","value":["57","70","72","109","84","163","104","147","59","72"]},{"algorithmname":"ts","areaid":"ffuzhou","examineid":1,"forecasid":"scmoc","value":["0.5976","0.85","0.9506","0.9136","0.7385","0.4068","0.7027","0.5455","0.2174","0.759"]},{"algorithmname":"sum","areaid":"ffuzhou","examineid":1,"forecasid":"scmoc","value":["82","80","81","81","65","59","74","55","69","83"]},{"algorithmname":"sum_n11","areaid":"ffuzhou","examineid":1,"forecasid":"scmoc","value":["49","68","77","74","48","24","52","30","15","63"]},{"algorithmname":"sum_n01","areaid":"ffuzhou","examineid":1,"forecasid":"scmoc","value":["29","7","0","0","10","17","5","12","52","4"]},{"algorithmname":"sum_n10","areaid":"ffuzhou","examineid":1,"forecasid":"scmoc","value":["4","5","4","7","7","18","17","13","2","16"]},{"algorithmname":"ts","areaid":"ganzhou","examineid":1,"forecasid":"scmoc","value":["0.3171","0.741","0.6667","0.6646","0.6852","0.5644","0.8221","0.4568","0.6792","0.816"]},{"algorithmname":"sum","areaid":"ganzhou","examineid":1,"forecasid":"scmoc","value":["164","166","162","161","162","163","163","162","159","163"]},{"algorithmname":"sum_n11","areaid":"ganzhou","examineid":1,"forecasid":"scmoc","value":["52","123","108","107","111","92","134","74","108","133"]},{"algorithmname":"sum_n01","areaid":"ganzhou","examineid":1,"forecasid":"scmoc","value":["101","24","32","35","10","14","16","2","33","4"]},{"algorithmname":"sum_n10","areaid":"ganzhou","examineid":1,"forecasid":"scmoc","value":["11","19","22","19","41","57","13","86","18","26"]},{"algorithmname":"ts","areaid":"jian","examineid":1,"forecasid":"scmoc","value":["0.4884","0.8391","0.8353","0.8519","0.5814","0.6905","0.6707","0.4706","0.3636","0.7528"]},{"algorithmname":"sum","areaid":"jian","examineid":1,"forecasid":"scmoc","value":["86","87","85","81","86","84","82","85","88","89"]},{"algorithmname":"sum_n11","areaid":"jian","examineid":1,"forecasid":"scmoc","value":["42","73","71","69","50","58","55","40","32","67"]},{"algorithmname":"sum_n01","areaid":"jian","examineid":1,"forecasid":"scmoc","value":["36","1","0","0","14","2","2","23","50","3"]},{"algorithmname":"sum_n10","areaid":"jian","examineid":1,"forecasid":"scmoc","value":["8","13","14","12","22","24","25","22","6","19"]},{"algorithmname":"ts","areaid":"jingdezhen","examineid":1,"forecasid":"scmoc","value":["0.7778","0.7778","0.8333","0.6667","0.8333","0.2308","0.7778","0.5","0.5789","1"]},{"algorithmname":"sum","areaid":"jingdezhen","examineid":1,"forecasid":"scmoc","value":["18","18","18","18","18","13","18","18","19","18"]},{"algorithmname":"sum_n11","areaid":"jingdezhen","examineid":1,"forecasid":"scmoc","value":["14","14","15","12","15","3","14","9","11","18"]},{"algorithmname":"sum_n01","areaid":"jingdezhen","examineid":1,"forecasid":"scmoc","value":["0","0","0","0","3","6","0","6","8","0"]},{"algorithmname":"sum_n10","areaid":"jingdezhen","examineid":1,"forecasid":"scmoc","value":["4","4","3","6","0","4","4","3","0","0"]},{"algorithmname":"ts","areaid":"jiujiang","examineid":1,"forecasid":"scmoc","value":["0.9459","0.9296","0.9296","0.8462","0.6667","0.6027","0.8974","0.7922","0.7403","0.9114"]},{"algorithmname":"sum","areaid":"jiujiang","examineid":1,"forecasid":"scmoc","value":["74","71","71","65","69","73","78","77","77","79"]},{"algorithmname":"sum_n11","areaid":"jiujiang","examineid":1,"forecasid":"scmoc","value":["70","66","66","55","46","44","70","61","57","72"]},{"algorithmname":"sum_n01","areaid":"jiujiang","examineid":1,"forecasid":"scmoc","value":["0","0","0","7","17","22","0","8","15","7"]},{"algorithmname":"sum_n10","areaid":"jiujiang","examineid":1,"forecasid":"scmoc","value":["4","5","5","3","6","7","8","8","5","0"]},{"algorithmname":"ts","areaid":"nanchang","examineid":1,"forecasid":"scmoc","value":["1","0.9714","1","0.8125","0.8611","0.4722","0.9429","0.7647","0.2941","0.8286"]},{"algorithmname":"sum","areaid":"nanchang","examineid":1,"forecasid":"scmoc","value":["35","35","35","32","36","36","35","34","34","35"]},{"algorithmname":"sum_n11","areaid":"nanchang","examineid":1,"forecasid":"scmoc","value":["35","34","35","26","31","17","33","26","10","29"]},{"algorithmname":"sum_n01","areaid":"nanchang","examineid":1,"forecasid":"scmoc","value":["0","0","0","3","5","5","0","8","24","6"]},{"algorithmname":"sum_n10","areaid":"nanchang","examineid":1,"forecasid":"scmoc","value":["0","1","0","3","0","14","2","0","0","0"]},{"algorithmname":"ts","areaid":"pingxiang","examineid":1,"forecasid":"scmoc","value":["1","1","1","0.75","0.5","0.5","0.8636","0.5455","0.1739","0.7826"]},{"algorithmname":"sum","areaid":"pingxiang","examineid":1,"forecasid":"scmoc","value":["16","18","20","16","18","18","22","22","23","23"]},{"algorithmname":"sum_n11","areaid":"pingxiang","examineid":1,"forecasid":"scmoc","value":["16","18","20","12","9","9","19","12","4","18"]},{"algorithmname":"sum_n01","areaid":"pingxiang","examineid":1,"forecasid":"scmoc","value":["0","0","0","0","7","9","1","10","13","2"]},{"algorithmname":"sum_n10","areaid":"pingxiang","examineid":1,"forecasid":"scmoc","value":["0","0","0","4","2","0","2","0","6","3"]},{"algorithmname":"ts","areaid":"shangrao","examineid":1,"forecasid":"scmoc","value":["0.86","0.7921","0.9118","0.7755","0.625","0.3021","0.7961","0.6095","0.213","0.8991"]},{"algorithmname":"sum","areaid":"shangrao","examineid":1,"forecasid":"scmoc","value":["100","101","102","98","96","96","103","105","108","109"]},{"algorithmname":"sum_n11","areaid":"shangrao","examineid":1,"forecasid":"scmoc","value":["86","80","93","76","60","29","82","64","23","98"]},{"algorithmname":"sum_n01","areaid":"shangrao","examineid":1,"forecasid":"scmoc","value":["3","12","0","1","34","43","6","35","85","10"]},{"algorithmname":"sum_n10","areaid":"shangrao","examineid":1,"forecasid":"scmoc","value":["11","9","9","21","2","24","15","6","0","1"]},{"algorithmname":"ts","areaid":"xinyu","examineid":1,"forecasid":"scmoc","value":["0.8889","0.8889","0.8889","0.5882","0.5625","0.9412","0.8333","0.5","0.2778","0.75"]},{"algorithmname":"sum","areaid":"xinyu","examineid":1,"forecasid":"scmoc","value":["18","18","18","17","16","17","18","18","18","16"]},{"algorithmname":"sum_n11","areaid":"xinyu","examineid":1,"forecasid":"scmoc","value":["16","16","16","10","9","16","15","9","5","12"]},{"algorithmname":"sum_n01","areaid":"xinyu","examineid":1,"forecasid":"scmoc","value":["0","0","0","0","7","0","0","8","11","1"]},{"algorithmname":"sum_n10","areaid":"xinyu","examineid":1,"forecasid":"scmoc","value":["2","2","2","7","0","1","3","1","2","3"]},{"algorithmname":"ts","areaid":"yichun","examineid":1,"forecasid":"scmoc","value":["0.8776","0.8776","0.8854","0.6552","0.5","0.6907","0.866","0.5313","0.25","0.8557"]},{"algorithmname":"sum","areaid":"yichun","examineid":1,"forecasid":"scmoc","value":["98","98","96","87","96","97","97","96","96","97"]},{"algorithmname":"sum_n11","areaid":"yichun","examineid":1,"forecasid":"scmoc","value":["86","86","85","57","48","67","84","51","24","83"]},{"algorithmname":"sum_n01","areaid":"yichun","examineid":1,"forecasid":"scmoc","value":["0","0","0","8","44","20","0","38","52","12"]},{"algorithmname":"sum_n10","areaid":"yichun","examineid":1,"forecasid":"scmoc","value":["12","12","11","22","4","10","13","7","20","2"]},{"algorithmname":"ts","areaid":"yingtan","examineid":1,"forecasid":"scmoc","value":["0.9333","0.875","0.875","0.6875","0.5","0.3125","0.875","0.6","0.1333","0.6875"]},{"algorithmname":"sum","areaid":"yingtan","examineid":1,"forecasid":"scmoc","value":["15","16","16","16","16","16","16","15","15","16"]},{"algorithmname":"sum_n11","areaid":"yingtan","examineid":1,"forecasid":"scmoc","value":["14","14","14","11","8","5","14","9","2","11"]},{"algorithmname":"sum_n01","areaid":"yingtan","examineid":1,"forecasid":"scmoc","value":["0","2","0","0","8","7","0","5","13","3"]},{"algorithmname":"sum_n10","areaid":"yingtan","examineid":1,"forecasid":"scmoc","value":["1","0","2","5","0","4","2","1","0","2"]},{"algorithmname":"ts","areaid":"jiangxi","examineid":1,"forecasid":"ybmode3","value":["0.755","0.8404","0.8594","0.7485","0.5796","0.6949","0.7762","0.5706","0.4391","0.6016"]},{"algorithmname":"sum","areaid":"jiangxi","examineid":1,"forecasid":"ybmode3","value":["706","708","704","672","678","672","706","687","706","728"]},{"algorithmname":"sum_n11","areaid":"jiangxi","examineid":1,"forecasid":"ybmode3","value":["533","595","605","503","393","467","548","392","310","438"]},{"algorithmname":"sum_n01","areaid":"jiangxi","examineid":1,"forecasid":"ybmode3","value":["114","53","40","78","145","91","23","170","298","31"]},{"algorithmname":"sum_n10","areaid":"jiangxi","examineid":1,"forecasid":"ybmode3","value":["59","60","59","91","140","114","135","125","98","259"]},{"algorithmname":"ts","areaid":"ffuzhou","examineid":1,"forecasid":"ybmode3","value":["0.878","0.8625","0.9506","0.9136","0.7077","0.8814","0.7568","0.4909","0.5362","0.5422"]},{"algorithmname":"sum","areaid":"ffuzhou","examineid":1,"forecasid":"ybmode3","value":["82","80","81","81","65","59","74","55","69","83"]},{"algorithmname":"sum_n11","areaid":"ffuzhou","examineid":1,"forecasid":"ybmode3","value":["72","69","77","74","46","52","56","27","37","45"]},{"algorithmname":"sum_n01","areaid":"ffuzhou","examineid":1,"forecasid":"ybmode3","value":["4","7","0","0","6","2","1","18","26","4"]},{"algorithmname":"sum_n10","areaid":"ffuzhou","examineid":1,"forecasid":"ybmode3","value":["6","4","4","7","13","5","17","10","6","34"]},{"algorithmname":"ts","areaid":"ganzhou","examineid":1,"forecasid":"ybmode3","value":["0.3354","0.7349","0.6975","0.6025","0.5123","0.4969","0.7914","0.5247","0.5786","0.4049"]},{"algorithmname":"sum","areaid":"ganzhou","examineid":1,"forecasid":"ybmode3","value":["164","166","162","161","162","163","163","162","159","163"]},{"algorithmname":"sum_n11","areaid":"ganzhou","examineid":1,"forecasid":"ybmode3","value":["55","122","113","97","83","81","129","85","92","66"]},{"algorithmname":"sum_n01","areaid":"ganzhou","examineid":1,"forecasid":"ybmode3","value":["104","32","40","46","10","16","17","6","19","5"]},{"algorithmname":"sum_n10","areaid":"ganzhou","examineid":1,"forecasid":"ybmode3","value":["5","12","9","18","69","66","17","71","48","92"]},{"algorithmname":"ts","areaid":"jian","examineid":1,"forecasid":"ybmode3","value":["0.7907","0.7126","0.8353","0.8519","0.4651","0.6429","0.4268","0.5294","0.375","0.573"]},{"algorithmname":"sum","areaid":"jian","examineid":1,"forecasid":"ybmode3","value":["86","87","85","81","86","84","82","85","88","89"]},{"algorithmname":"sum_n11","areaid":"jian","examineid":1,"forecasid":"ybmode3","value":["68","62","71","69","40","54","35","45","33","51"]},{"algorithmname":"sum_n01","areaid":"jian","examineid":1,"forecasid":"ybmode3","value":["5","14","0","0","9","6","5","22","37","1"]},{"algorithmname":"sum_n10","areaid":"jian","examineid":1,"forecasid":"ybmode3","value":["13","11","14","12","37","24","42","18","18","37"]},{"algorithmname":"ts","areaid":"jingdezhen","examineid":1,"forecasid":"ybmode3","value":["0.7778","0.7778","0.8333","0.6111","0.8333","0.6923","0.7778","0.5","0.5789","1"]},{"algorithmname":"sum","areaid":"jingdezhen","examineid":1,"forecasid":"ybmode3","value":["18","18","18","18","18","13","18","18","19","18"]},{"algorithmname":"sum_n11","areaid":"jingdezhen","examineid":1,"forecasid":"ybmode3","value":["14","14","15","11","15","9","14","9","11","18"]},{"algorithmname":"sum_n01","areaid":"jingdezhen","examineid":1,"forecasid":"ybmode3","value":["0","0","0","0","3","4","0","6","8","0"]},{"algorithmname":"sum_n10","areaid":"jingdezhen","examineid":1,"forecasid":"ybmode3","value":["4","4","3","7","0","0","4","3","0","0"]},{"algorithmname":"ts","areaid":"jiujiang","examineid":1,"forecasid":"ybmode3","value":["0.9324","0.9296","0.9296","0.8462","0.6957","0.7808","0.8974","0.7922","0.7792","0.7975"]},{"algorithmname":"sum","areaid":"jiujiang","examineid":1,"forecasid":"ybmode3","value":["74","71","71","65","69","73","78","77","77","79"]},{"algorithmname":"sum_n11","areaid":"jiujiang","examineid":1,"forecasid":"ybmode3","value":["69","66","66","55","48","57","70","61","60","63"]},{"algorithmname":"sum_n01","areaid":"jiujiang","examineid":1,"forecasid":"ybmode3","value":["1","0","0","7","20","9","0","8","17","5"]},{"algorithmname":"sum_n10","areaid":"jiujiang","examineid":1,"forecasid":"ybmode3","value":["4","5","5","3","1","7","8","8","0","11"]},{"algorithmname":"ts","areaid":"nanchang","examineid":1,"forecasid":"ybmode3","value":["1","0.9714","1","0.75","0.6944","0.8611","0.9429","0.7647","0.2647","0.6857"]},{"algorithmname":"sum","areaid":"nanchang","examineid":1,"forecasid":"ybmode3","value":["35","35","35","32","36","36","35","34","34","35"]},{"algorithmname":"sum_n11","areaid":"nanchang","examineid":1,"forecasid":"ybmode3","value":["35","34","35","24","25","31","33","26","9","24"]},{"algorithmname":"sum_n01","areaid":"nanchang","examineid":1,"forecasid":"ybmode3","value":["0","0","0","8","11","4","0","8","25","4"]},{"algorithmname":"sum_n10","areaid":"nanchang","examineid":1,"forecasid":"ybmode3","value":["0","1","0","0","0","1","2","0","0","7"]},{"algorithmname":"ts","areaid":"pingxiang","examineid":1,"forecasid":"ybmode3","value":["1","1","1","0.875","0.6667","0.6667","0.8182","0.5455","0.1739","0.6957"]},{"algorithmname":"sum","areaid":"pingxiang","examineid":1,"forecasid":"ybmode3","value":["16","18","20","16","18","18","22","22","23","23"]},{"algorithmname":"sum_n11","areaid":"pingxiang","examineid":1,"forecasid":"ybmode3","value":["16","18","20","14","12","12","18","12","4","16"]},{"algorithmname":"sum_n01","areaid":"pingxiang","examineid":1,"forecasid":"ybmode3","value":["0","0","0","0","4","6","0","10","11","0"]},{"algorithmname":"sum_n10","areaid":"pingxiang","examineid":1,"forecasid":"ybmode3","value":["0","0","0","2","2","0","4","0","8","7"]},{"algorithmname":"ts","areaid":"shangrao","examineid":1,"forecasid":"ybmode3","value":["0.88","0.9109","0.9118","0.8061","0.7188","0.7188","0.7961","0.5619","0.3056","0.7064"]},{"algorithmname":"sum","areaid":"shangrao","examineid":1,"forecasid":"ybmode3","value":["100","101","102","98","96","96","103","105","108","109"]},{"algorithmname":"sum_n11","areaid":"shangrao","examineid":1,"forecasid":"ybmode3","value":["88","92","93","79","69","69","82","59","33","77"]},{"algorithmname":"sum_n01","areaid":"shangrao","examineid":1,"forecasid":"ybmode3","value":["0","0","0","2","22","24","0","40","75","7"]},{"algorithmname":"sum_n10","areaid":"shangrao","examineid":1,"forecasid":"ybmode3","value":["12","9","9","17","5","3","21","6","0","25"]},{"algorithmname":"ts","areaid":"xinyu","examineid":1,"forecasid":"ybmode3","value":["0.8889","0.8889","0.8889","0.5882","0.3125","0.9412","0.8333","0.5","0.2222","0.6875"]},{"algorithmname":"sum","areaid":"xinyu","examineid":1,"forecasid":"ybmode3","value":["18","18","18","17","16","17","18","18","18","16"]},{"algorithmname":"sum_n11","areaid":"xinyu","examineid":1,"forecasid":"ybmode3","value":["16","16","16","10","5","16","15","9","4","11"]},{"algorithmname":"sum_n01","areaid":"xinyu","examineid":1,"forecasid":"ybmode3","value":["0","0","0","1","6","0","0","8","12","0"]},{"algorithmname":"sum_n10","areaid":"xinyu","examineid":1,"forecasid":"ybmode3","value":["2","2","2","6","5","1","3","1","2","5"]},{"algorithmname":"ts","areaid":"yichun","examineid":1,"forecasid":"ybmode3","value":["0.8776","0.8776","0.8854","0.6782","0.375","0.7732","0.866","0.5313","0.2604","0.5773"]},{"algorithmname":"sum","areaid":"yichun","examineid":1,"forecasid":"ybmode3","value":["98","98","96","87","96","97","97","96","96","97"]},{"algorithmname":"sum_n11","areaid":"yichun","examineid":1,"forecasid":"ybmode3","value":["86","86","85","59","36","75","84","51","25","56"]},{"algorithmname":"sum_n01","areaid":"yichun","examineid":1,"forecasid":"ybmode3","value":["0","0","0","14","53","16","0","38","56","3"]},{"algorithmname":"sum_n10","areaid":"yichun","examineid":1,"forecasid":"ybmode3","value":["12","12","11","14","7","6","13","7","15","38"]},{"algorithmname":"ts","areaid":"yingtan","examineid":1,"forecasid":"ybmode3","value":["0.9333","1","0.875","0.6875","0.875","0.6875","0.75","0.5333","0.1333","0.6875"]},{"algorithmname":"sum","areaid":"yingtan","examineid":1,"forecasid":"ybmode3","value":["15","16","16","16","16","16","16","15","15","16"]},{"algorithmname":"sum_n11","areaid":"yingtan","examineid":1,"forecasid":"ybmode3","value":["14","16","14","11","14","11","12","8","2","11"]},{"algorithmname":"sum_n01","areaid":"yingtan","examineid":1,"forecasid":"ybmode3","value":["0","0","0","0","1","4","0","6","12","2"]},{"algorithmname":"sum_n10","areaid":"yingtan","examineid":1,"forecasid":"ybmode3","value":["1","0","2","5","1","1","4","1","1","3"]}]}
     * errmsg : success
     * errcode : 0
     */

    private DataBean data;
    private String errmsg;
    private String errcode;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public static class DataBean {
        private List<Integer> agings;
        private List<ForecasBean> forecas;

        public List<Integer> getAgings() {
            return agings;
        }

        public void setAgings(List<Integer> agings) {
            this.agings = agings;
        }

        public List<ForecasBean> getForecas() {
            return forecas;
        }

        public void setForecas(List<ForecasBean> forecas) {
            this.forecas = forecas;
        }

        public static class ForecasBean {
            /**
             * algorithmname : ts
             * areaid : jiangxi
             * examineid : 1
             * forecasid : guidance
             * value : ["0.7564","0.846","0.8594","0.7485","0.5796","0.6949","0.7762","0.5706","0.4391","0.6016"]
             */

            private String algorithmname;
            private String areaid;
            private int examineid;
            private String forecasid;
            private List<String> value;

            public String getAlgorithmname() {
                return algorithmname;
            }

            public void setAlgorithmname(String algorithmname) {
                this.algorithmname = algorithmname;
            }

            public String getAreaid() {
                return areaid;
            }

            public void setAreaid(String areaid) {
                this.areaid = areaid;
            }

            public int getExamineid() {
                return examineid;
            }

            public void setExamineid(int examineid) {
                this.examineid = examineid;
            }

            public String getForecasid() {
                return forecasid;
            }

            public void setForecasid(String forecasid) {
                this.forecasid = forecasid;
            }

            public List<String> getValue() {
                return value;
            }

            public void setValue(List<String> value) {
                this.value = value;
            }
        }
    }
}