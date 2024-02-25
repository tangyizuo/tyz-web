package com.tyz.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyz.common.QueryPageParam;
import com.tyz.common.Result;
import com.tyz.domain.User;
import com.tyz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author springboot_01
 * @since 2024-02-21
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public List<User> list(){
        return userService.list();
    }

    //查询单个
    @GetMapping("/findByNo")
    public Result findByNo(@RequestParam String no){

        List list = userService.lambdaQuery().eq(User::getNo, no).list();
        return list.size() > 0 ? Result.success(list):Result.fail();
    }

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody User user){
        return userService.save(user)?Result.success():Result.fail();
    }

    //更新
    @PostMapping("/update")
    public Result update(@RequestBody User user){
        return userService.updateById(user)?Result.success():Result.fail();
    }


    @GetMapping("/del")
    public Result del(@RequestParam String id){
        return userService.removeById(id)?Result.success():Result.fail();
    }


    @PostMapping("/login")
    public Result login(@RequestBody User user){
        List list = userService.lambdaQuery().eq(User::getNo,user.getNo())
                .eq(User::getPassword,user.getPassword()).list();
        return list.size()>0?Result.success(list.get(0)):Result.fail();
    }


    //修改
    @PostMapping("/mod")
    public boolean mod(@RequestBody User user){
        return userService.updateById(user);
    }

    //修改或新增
    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody User user){
        return userService.saveOrUpdate(user);
    }

    //删除
    @GetMapping("/delete")
    public boolean delete(Integer id){
        return userService.removeById(id);
    }

    //分页查询
    @PostMapping("/listP")
    public Result listP(@RequestBody User user){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(user.getName())){
            lambdaQueryWrapper.like(User::getName,user.getName());
        }

        return Result.success(userService.list(lambdaQueryWrapper));
    }


    @PostMapping("/listPage")
    public List<User> listPage(@RequestBody QueryPageParam query){

        System.out.println("num=="+query.getPageNum());
        System.out.println("size=="+query.getPageSize());

        HashMap param = query.getParam();
        String name = (String) param.get("name");
        System.out.println("name=="+(String)param.get("name"));
//        System.out.println("no=="+(String)param.get("no"));

//        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        lambdaQueryWrapper.like(User::getName,user.getName());
//        return userService.list(lambdaQueryWrapper);

        Page<User> page = new Page<User>(1,2);
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(User::getName,name);

        IPage result = userService.page(page, lambdaQueryWrapper);
        System.out.println("total=="+result.getTotal());
        return result.getRecords();
    }

    @PostMapping("/listPageC1")
    public Result listPageC1(@RequestBody QueryPageParam query){

        HashMap param = query.getParam();
        String name = (String) param.get("name");
        String sex = (String) param.get("sex");

        Page<User> page = new Page<User>(1,2);
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(User::getName,name);
        }
        if(StringUtils.isNotBlank(sex)){
            lambdaQueryWrapper.eq(User::getSex,sex);
        }


//        IPage result = userService.pageC(page);
        IPage result = userService.pageCC(page,lambdaQueryWrapper);
        System.out.println("total=="+result.getTotal());
        return Result.success(result.getRecords(), result.getTotal());
    }
}
