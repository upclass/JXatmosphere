function loadALineChart(){
    // 必须加JOSN.parse 转换数据类型
    var option = JSON.parse(Android.getLineChartOptions());
    var chart2Doc = document.getElementById('chart2');
    var myChart2 = require('echarts').init(chart2Doc);
    myChart2.setOption(option);
}


function loadAZhuzhuangChart(){
    // 必须加JOSN.parse 转换数据类型
    var option = JSON.parse(Android.getZhuZhuangChartOptions());
    var chart2Doc = document.getElementById('chart2');
    var myChart2 = require('echarts').init(chart2Doc);
    myChart2.setOption(option);
}
