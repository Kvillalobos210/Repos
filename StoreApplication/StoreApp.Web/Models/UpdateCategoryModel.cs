﻿using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace StoreApp.Web.Models
{
    public class UpdateCategoryModel
    {
        public int CategoryId { get; set; }
        [Required(ErrorMessage = "Todo producto debe tener un nombre")]
        [StringLength(50, MinimumLength = 3, ErrorMessage = "Nombre de la categoria debe tener entre 3 a 50 caracteres")]
        public string Name { get; set; }
        [StringLength(250, ErrorMessage = "La descripcion debe tener un maximo de 250 caracteres")]
        public string Description { get; set; }
    }
}