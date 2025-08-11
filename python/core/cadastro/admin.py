from django.contrib import admin
from .models import Cadastro

#admin.site.register(Cadastro)
@admin.register(Cadastro)
class CadastroAdmin(admin.ModelAdmin):
    list_display = ('id_usuario','nome', 'checkin', 'latitude', 'longitude')