$(function () {
    $("#button-fetch").on("click", function () {
    	const position=$("#input-position").val();
    	console.log("position="+position);
        $.ajax({
            type: "get",
            url: "/job/list",
            data:"position="+position,
            dataType: "json",
            success: function (response) {
                if(response.state==200){
                    initPie(response.data);
                }else{
                    alert(response.message);
                }
            }
        });
    });
    $("#button-test").on("click", function () {
        $.ajax({
            type: "get",
            url: "/job/test",
            dataType: "json",
            success: function (response) {
                console.log(JSON.stringify(response));
            }
        });
    });
    $("#button-position").on("click", function () {
        if(!confirm("确定？")){
            return;
        }
    	$.ajax({
    		type: "get",
    		url: "/position/",
    		dataType: "json",
    		success: function (response) {
    			console.log(JSON.stringify(response));
    		}
    	});
    });
});

function initPie(data) {
    var legendData = [];
    data.technologyCounter.forEach(e => {
        legendData.push(e.name);
    });
    var chart = echarts.init(document.getElementById('div-result'),"macarons");
    option = {
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            x: 'left',
            type: 'scroll',
            data: legendData
        },
        series: [
            //内圈
            {
                name: '次数',
                type: 'pie',
                selectedMode: 'single',
                avoidLabelOverlap: true,
                radius: [0, '55%'],

                label: {
                    normal: {
                        position: 'inner'
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data: data.technologyTypeCounter
            },
            //外圈
            {
                name: '次数',
                type: 'pie',
                radius: ['60%', '90%'],
                avoidLabelOverlap: true,
                label: {
                    normal: {
                        // formatter: '{a|{a}}{abg|}\n{hr|}\n  {b|{b}：}{c}  {per|{d}%}  ',
                        formatter: '  {b}：{c} - {d}%  ',
                        backgroundColor: '#eee',
                        borderColor: '#aaa',
                        borderWidth: 1,
                        borderRadius: 4,
                        // shadowBlur:3,
                        // shadowOffsetX: 2,
                        // shadowOffsetY: 2,
                        // shadowColor: '#999',
                        // padding: [0, 7],
                        rich: {
                            a: {
                                color: '#999',
                                lineHeight: 22,
                                align: 'center'
                            },
                            // abg: {
                            //     backgroundColor: '#333',
                            //     width: '100%',
                            //     align: 'right',
                            //     height: 22,
                            //     borderRadius: [4, 4, 0, 0]
                            // },
                            hr: {
                                borderColor: '#aaa',
                                width: '100%',
                                borderWidth: 0.5,
                                height: 0
                            },
                            b: {
                                fontSize: 16,
                                lineHeight: 33
                            },
                            per: {
                                color: '#eee',
                                backgroundColor: '#334455',
                                padding: [2, 4],
                                borderRadius: 2
                            }
                        }
                    }
                },
                data: data.technologyCounter
            }
        ]
    };
    chart.setOption(option);

}