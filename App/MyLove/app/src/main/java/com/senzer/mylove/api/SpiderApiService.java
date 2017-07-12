package com.senzer.mylove.api;

import com.senzer.mylove.entity.dto.ReqLocation;
import com.senzer.mylove.entity.vo.HeadUrl;
import com.senzer.mylove.entity.vo.UserInfo;
import com.squareup.okhttp.RequestBody;

import java.util.Map;

import retrofit.Response;
import retrofit.http.Body;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.PartMap;
import rx.Observable;

/**
 * ProjectName: SpiderApiService
 * Description: retrofit理解：
 * 1. 如果在@GET, @POST...上面加入的常量中包含HOST，会优先选择该HOST作为链接主机
 * 2. 如果不存在HOST，那么就会使用在实例化时的的HOST，作为链接主机
 * <p>
 * author: JeyZheng
 * version: 4.0
 * created at: 2016/8/16 17:29
 */
@SuppressWarnings("ALL")
public interface SpiderApiService {
    /**
     * 常规用户登录
     *
     * @param param
     * @return
     */
    @FormUrlEncoded
    @POST(HttpUrls.USER_LOGIN)
    Observable<Response<DataResponse<UserInfo>>> userLogin(@FieldMap Map<String, String> param);

    /**
     * 更新定位信息，FROM表单提交
     *
     * @param param
     * @return
     */
    @FormUrlEncoded
    @POST(HttpUrls.UPDATE_LOCATION)
    Observable<DataResponse> updateLocation(@FieldMap Map<String, String> param);

    /**
     * 更新定位信息，JSON格式提交
     *
     * @param param
     * @return
     */
    @POST(HttpUrls.UPDATE_LOCATION_BY_JSON)
    Observable<DataResponse> updateLocation(@Body ReqLocation reqLocation);

    /**
     * 设置用户头像
     *
     * @param param map
     * @param image
     * @return
     */
    @Multipart
    @POST(HttpUrls.UPLOAD_HEADER_IMG)
    Observable<DataResponse<HeadUrl>> uploadHeaderImg(@PartMap Map<String, String> param,
                                                      @Part("header\"; filename=\"image.png") RequestBody image);

    /**
     * 设置用户头像
     * <p>
     * Content-Disposition: form-data; name="file"; filename="head_icon.png"
     * Content-Transfer-Encoding: binary
     * Content-Type: image/png
     * Content-Length: 59699
     * <p>
     * 1. 由上可得，retrofit需要有name与filename两个字段
     * 2. name="file"；后台的form字段名；
     * 3. filename="head_icon.png"：默认的文件名（必须要有这个字段，否则会报错，后台获取到文件，可以任意修改文件名）
     * 4. 但是这样写@Part("header")，最终只能得出 name="header"
     * 5. 所以为了得到 name="file"; filename="head_icon.png"
     * 6. 我们就自己拼接了filename，最终结果：@Part("header\"; filename="head_icon.png")
     * <p>
     * 注意：
     * 在@Multipart中
     * 1. 参数类型：Content-Type: text/plain; charset=utf-8
     * 2. 所以必须要封装一下：RequestBody.create(MediaType.parse("text/plain"), "123456")
     * 3. 文件类型：Content-Type: image/png
     * 4. 所以也要封装一下：RequestBody.create(MediaType.parse("multipart/form-data"), new File(...))
     *
     * @param param
     * @param image
     * @return
     */
    @Multipart
    @POST(HttpUrls.UPLOAD_HEADER_IMG)
    Observable<DataResponse<HeadUrl>> uploadHeaderImgReqBody(@PartMap Map<String, RequestBody> param,
                                                             @Part("header\"; filename=\"image.png") RequestBody image);
    }
