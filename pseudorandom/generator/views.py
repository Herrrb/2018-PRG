from django.shortcuts import render, HttpResponse, redirect
from django.http import JsonResponse
from .pr import generator
from django.views.decorators.csrf import csrf_exempt
import re
import os
import time
# Create your views here.


def trans_time():
    t = time.time()
    t1 = str(t).split('.')
    num_mi = len(t1[0]) - len(t1[1])
    t += int(t1[1]) * 10**num_mi
    return t


def get_first_value():
    f1 = os.getpid() * trans_time() / 10 ** 14
    f2 = os.getppid() * trans_time() / 10 ** 15
    fs = trans_time() / 10 ** 10
    f1 -= int(f1)
    f2 -= int(f2)
    fs -= int(fs)
    return f1, f2, fs


@ csrf_exempt
def index(request):
    if request.method == 'GET':
        return render(request, 'index.html', locals())
    # 随机获取三个初始值
    vl = {'B': 1, 'KB': 2**10, 'MB': 2**20}
    ll = ['B', 'KB', 'MB']
    f1, f2, fs = get_first_value()
    size = request.POST.get('size', 'None')
    try:
        re_groups = re.match('(\d+\.\d+|\d+)([a-zA-Z]+)', size).groups()
    except AttributeError as e:
        message = '请按照标准输入'
        return render(request, 'index.html', locals())
    number_size = float(re_groups[0])
    unit_size = re_groups[1]

    if size == 'None':
        message = '出错了'
        return render(request, 'index.html', locals())
    elif unit_size.upper() not in ll:
        message = '请按照标准输入'
        return render(request, 'index.html', locals())

    try:
        size = number_size * vl[unit_size.upper()] + 30
    except TypeError as e:
        message = '请按标准输入'
        return render(request, 'index.html', locals())

    pseudo_r, num_0, num_1 = generator(int(size), f1, f2, fs)

    return render(request, 'index.html', locals())


def api(request):
    if request.method == 'GET':
        size = request.GET.get('size', None)
        size = int(size) + 30
        f1, f2, fs = get_first_value()
        pseudo_r, num_0, num_1 = generator(size, f1, f2, fs)
        result = {'num_0': num_0, 'num_1': num_1, 'pseudo_random': pseudo_r}
        return JsonResponse(result)
    else:
        return HttpResponse("There are something wrong!")
