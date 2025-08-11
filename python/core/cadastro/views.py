from django.shortcuts import render,redirect
from django.http.response import HttpResponse

def homePage(request):
    return HttpResponse("Estou na home!!!")
    
