from ninja import NinjaAPI
from pydantic import BaseModel
from .models import Cadastro
from django.shortcuts import get_object_or_404,HttpResponse
from django.forms.models import model_to_dict

api = NinjaAPI()

# @api.get('checkin_api/')
# def getCoordinates(request):
#     cadastros = Cadastro.objects.all()
#     datas_json = [{"nome": p.nome, "latitude" : p.latitude, "longitude":p.longitude} for p in cadastros]
#     return datas_json

@api.get('checkin_api/{id_usuario}')
def getCoordinates(request, id_usuario:int):
    dados = Cadastro.objects.filter(id_usuario = id_usuario)
    return [{"nome":i.nome, "long":i.longitude,"lat":i.latitude } for i in dados ][0]

class UpdateCheckinSchema(BaseModel):
    checkin: str

@api.post('checkin_api/{id_usuario}/update_checkin/')
def update_checkin(request, id_usuario: int, payload: UpdateCheckinSchema):
    cadastro = get_object_or_404(Cadastro, id_usuario=id_usuario)
    print("payload: ",payload.checkin)
    cadastro.checkin = payload.checkin
    cadastro.save()
    return {"success": True, "message": "Check-in atualizado com sucesso.", "id_usuario": id_usuario, "checkin": cadastro.checkin}
